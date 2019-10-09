imds = imageDatastore('/media/titan/Storage 1/256_ObjectCategories',...
    'IncludeSubfolders', true, ...
    'LabelSource', 'foldernames');

tbl = countEachLabel(imds);
minSetCount = min(tbl{:,2}); % determine the smallest amount of images in a category

% SETUP

% number of images from each class to be placed in the dataset
% use minSetCount to use the largest possible number
% numImgs = 60
% ratio of training data to test data
trainRatio = .6
% mini batch size: uses more memory when higher but can speed up stuff 
% 32 usually reasonable, decrease if running out of memory
miniSize = 1

% Use splitEachLabel method to trim the set.
imds1 = imds

% Notice that each set now has exactly the same number of images.
countEachLabel(imds1)

% Load pre-trained AlexNet
net1 = alexnet();

% View the CNN architecture
net1.Layers

% Take the CNN layers and add new classification layers at the end.
prebuiltLayers = net1.Layers(1:end-3);
layers = [prebuiltLayers;fullyConnectedLayer(257);softmaxLayer;classificationLayer];

% Number of class names for ImageNet classification task
numel(net1.Layers(end).ClassNames)

% Set the ImageDatastore ReadFcn
imds1.ReadFcn = @(filename)readAndPreprocessImage1(filename);

[trainingSet1, testSet1] = splitEachLabel(imds1, trainRatio, 'randomized');

% set training options
options = trainingOptions('sgdm','InitialLearnRate',0.001,...
    'MaxEpochs',30,...
    'ExecutionEnvironment', 'multi-gpu',...
    'Plots','training-progress');

 
% train alexnet on dataset
alexnet101 = trainNetwork(trainingSet1,layers,options);

% make predictions
preds = classify(alexnet101,testSet1);

% compare to reality
truetest = testSet1.Labels;
nnz(preds == truetest)/numel(preds)

% view confusion matrix
[confmat, order] = confusionmat(truetest,preds);
heatmap(order,order,confmat);

function Iout = readAndPreprocessImage1(filename)

        I = imread(filename);

        % Some images may be grayscale. Replicate the image 3 times to
        % create an RGB image.
        if ismatrix(I)
            I = cat(3,I,I,I);
        end

        % Resize the image as required for Alexnet.
        Iout = imresize(I, [227 227]);

        % Note that the aspect ratio is not preserved. In Caltech 101, the
        % object of interest is centered in the image and occupies a
        % majority of the image scene. Therefore, preserving the aspect
        % ratio is not critical. However, for other data sets, it may prove
        % beneficial to preserve the aspect ratio of the original image
        % when resizing.
end
