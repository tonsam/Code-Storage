% new dcnn fusion code

imds = imageDatastore('/media/titan/Storage 3/ImageNet/ILSVRC2014_DET_train/categories',...
    'IncludeSubfolders', true, ...
    'LabelSource', 'foldernames');

tbl = countEachLabel(imds);

% Using whole dataaset, trimming unneccesary
imdsTrimmed = imds

% Set the ImageDatastore ReadFcn and preprocess
imdsTrimmed.ReadFcn = @(filename)readAndPreprocessImage(filename);

[imdsTrain,imdsTest] = splitEachLabel(imdsTrimmed,0.7,'randomized');

numTrainImages = numel(imdsTrain.Labels);

net = vgg19;

net.Layers

inputSize = net.Layers(1).InputSize

augimdsTrain = augmentedImageDatastore(inputSize(1:2),imdsTrain);
augimdsTest = augmentedImageDatastore(inputSize(1:2),imdsTest);

disp("Extracting Features");

layer = 'fc7';
featuresTrain = activations(net,augimdsTrain,layer,...
    'MiniBatchSize', 32,'OutputAs','rows',...
    'ExecutionEnvironment','gpu');

featuresTest = activations(net,augimdsTest,layer,...
    'MiniBatchSize', 32,'OutputAs','rows',...
    'ExecutionEnvironment','gpu');

YTrain = imdsTrain.Labels;
YTest = imdsTest.Labels;

disp("Training SVM from Features");

classifier = fitcecoc(featuresTrain,YTrain,...
    'Learners', 'Linear', 'Coding', 'onevsall', 'ObservationsIn', 'rows');

YPred = predict(classifier,featuresTest);

accuracy = mean(YPred == YTest)

function Iout = readAndPreprocessImage(filename)

        I = imread(filename);

        % Some images may be grayscale. Replicate the image 3 times to
        % create an RGB image.
        if ismatrix(I)
            I = cat(3,I,I,I);
        end

        % Resize the image as required for vgg.
        Iout = imresize(I, [224 224]);

        % Note that the aspect ratio is not preserved. In Caltech 101, the
        % object of interest is centered in the image and occupies a
        % majority of the image scene. Therefore, preserving the aspect
        % ratio is not critical. However, for other data sets, it may prove
        % beneficial to preserve the aspect ratio of the original image
        % when resizing.
end