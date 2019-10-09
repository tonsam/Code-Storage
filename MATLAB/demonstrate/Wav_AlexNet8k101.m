% new dcnn fusion code

imdsH = imageDatastore('/media/titan/Storage 1/WavH_101_ObjectCategories',...
    'IncludeSubfolders', true, ...
    'LabelSource', 'foldernames');

imdsV = imageDatastore('/media/titan/Storage 1/WavV_101_ObjectCategories',...
    'IncludeSubfolders', true, ...
    'LabelSource', 'foldernames');

imdsD = imageDatastore('/media/titan/Storage 1/WavD_101_ObjectCategories',...
    'IncludeSubfolders', true, ...
    'LabelSource', 'foldernames');

tbl = countEachLabel(imdsH);

% numImgs = 30;
imdsTrimmedH = imdsH
imdsTrimmedV = imdsV;
imdsTrimmedD = imdsD;

% Set the ImageDatastore ReadFcn and preprocess
imdsTrimmedH.ReadFcn = @(filename)readAndPreprocessImage(filename);
imdsTrimmedV.ReadFcn = @(filename)readAndPreprocessImage(filename);
imdsTrimmedD.ReadFcn = @(filename)readAndPreprocessImage(filename);

[imdsTrainH,imdsTestH] = splitEachLabel(imdsTrimmedH,0.7,'randomized');
[imdsTrainV,imdsTestV] = splitEachLabel(imdsTrimmedV,0.7,'randomized');
[imdsTrainD,imdsTestD] = splitEachLabel(imdsTrimmedD,0.7,'randomized');

%numTrainImages = numel(imdsTrain.Labels);
%numTrainImages = numel(imdsTrain.Labels);
%numTrainImages = numel(imdsTrain.Labels);

net = alexnet;

net.Layers;

inputSize = net.Layers(1).InputSize;

% augimdsTrain = augmentedImageDatastore(inputSize(1:2),imdsTrain);
% augimdsTest = augmentedImageDatastore(inputSize(1:2),imdsTest);

disp("Extracting Features");

layer = 'fc7';
featuresTrainH = activations(net,imdsTrainH,layer,...
    'MiniBatchSize', 1,'OutputAs','rows');
featuresTrainV = activations(net,imdsTrainV,layer,...
    'MiniBatchSize', 1,'OutputAs','rows');
featuresTrainD = activations(net,imdsTrainD,layer,...
    'MiniBatchSize', 1,'OutputAs','rows');

% test fusion types
featuresFusedMax = max(featuresTrainH,featuresTrainV);
featuresFusedMax = max(featuresFusedMax,featuresTrainD);

featuresFusedMin = min(featuresTrainH,featuresTrainV);
featuresFusedMin = min(featuresFusedMin,featuresTrainD);

featuresFusedMean = mean(cat(3,featuresTrainH,featuresTrainV,featuresTrainD),3);

featuresFusedStack = featuresTrainH + featuresTrainV + featuresTrainD;


featuresTestH = activations(net,imdsTestH,layer,...
    'MiniBatchSize', 1,'OutputAs','rows');
featuresTestV = activations(net,imdsTestV,layer,...
    'MiniBatchSize', 1,'OutputAs','rows');
featuresTestD = activations(net,imdsTestD,layer,...
    'MiniBatchSize', 1,'OutputAs','rows');

featuresTestFusedMax = max(featuresTestH,featuresTestV);
featuresTestFusedMax = max(featuresTestFusedMax,featuresTestD);

featuresTestFusedMin = min(featuresTestH,featuresTestV);
featuresTestFusedMin = min(featuresTestFusedMin,featuresTestD);

featuresTestFusedMean = mean(cat(3,featuresTestH,featuresTestV,featuresTestD),3);

featuresTestFusedStack = featuresTestH + featuresTestV + featuresTestD;

% horizontal features
YTrain = imdsTrainH.Labels;
YTest = imdsTestH.Labels;
disp("Training SVM from Horizontal Features");
classifier = fitcecoc(featuresTrainH,YTrain,...
    'Learners', 'Linear', 'Coding', 'onevsall', 'ObservationsIn', 'rows');
YPred = predict(classifier,featuresTestH);
horizAccuracy = mean(YPred == YTest)

% vertical features
YTrain = imdsTrainV.Labels;
YTest = imdsTestV.Labels;
disp("Training SVM from Vertical Features");
classifier = fitcecoc(featuresTrainV,YTrain,...
    'Learners', 'Linear', 'Coding', 'onevsall', 'ObservationsIn', 'rows');
YPred = predict(classifier,featuresTestV);
vertAccuracy = mean(YPred == YTest)


% diagonal features
YTrain = imdsTrainD.Labels;
YTest = imdsTestD.Labels;
disp("Training SVM from Diagonal Features");
classifier = fitcecoc(featuresTrainD,YTrain,...
    'Learners', 'Linear', 'Coding', 'onevsall', 'ObservationsIn', 'rows');
YPred = predict(classifier,featuresTestD);
diagAccuracy = mean(YPred == YTest)

% fused min features
YTrain = imdsTrainH.Labels;
YTest = imdsTestH.Labels;
disp("Training SVM from Fused Min Features");
classifier = fitcecoc(featuresFusedMin,YTrain,...
    'Learners', 'Linear', 'Coding', 'onevsall', 'ObservationsIn', 'rows');
YPred = predict(classifier,featuresTestFusedMin);
minAccuracy = mean(YPred == YTest)


% fused max features
YTrain = imdsTrainH.Labels;
YTest = imdsTestH.Labels;
disp("Training SVM from Fused Max Features");
classifier = fitcecoc(featuresFusedMax,YTrain,...
    'Learners', 'Linear', 'Coding', 'onevsall', 'ObservationsIn', 'rows');
YPred = predict(classifier,featuresTestFusedMax);
maxAccuracy = mean(YPred == YTest)


% fused mean features
YTrain = imdsTrainH.Labels;
YTest = imdsTestH.Labels;
disp("Training SVM from Fused Mean Features");
classifier = fitcecoc(featuresFusedMean,YTrain,...
    'Learners', 'Linear', 'Coding', 'onevsall', 'ObservationsIn', 'rows');
YPred = predict(classifier,featuresTestFusedMean);
meanAccuracy = mean(YPred == YTest)


% fused stacked features
YTrain = imdsTrainH.Labels;
YTest = imdsTestH.Labels;
disp("Training SVM from Fused Stacked Features");
classifier = fitcecoc(featuresFusedStack,YTrain,...
    'Learners', 'Linear', 'Coding', 'onevsall', 'ObservationsIn', 'rows');
YPred = predict(classifier,featuresTestFusedStack);
stackedAccuracy = mean(YPred == YTest)



function Iout = readAndPreprocessImage(filename)

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