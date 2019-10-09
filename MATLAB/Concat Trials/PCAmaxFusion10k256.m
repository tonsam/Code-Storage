% dc1 = dual network fusion, configuration 1 (alexnet + vgg16)

imds = imageDatastore('/media/titan/Storage 1/256_ObjectCategories',...
    'IncludeSubfolders', true, ...
    'LabelSource', 'foldernames');

tbl = countEachLabel(imds);

numImgs = 40;
imdsTrimmed = splitEachLabel(imds, numImgs, 'randomized');
imdsTrimmed2 = imdsTrimmed;

% Alexnet and vgg16/19 use different sized input images.
% Use splitEachLabel method to trim two sets, for Alexnet and VGG based nets.
imdsTrimmed.ReadFcn = @(filename)readAndPreprocessImageA(filename);
[imdsTrainA,imdsTestA] = splitEachLabel(imdsTrimmed,0.6,'randomized');

imdsTrimmed2.ReadFcn = @(filename)readAndPreprocessImageV(filename);
[imdsTrainV,imdsTestV] = splitEachLabel(imdsTrimmed2,0.6,'randomized');

% select three pretrained networks
net1 = alexnet;
net2 = vgg16;
net3 = vgg19;

disp("Extracting Features");

% set layer to fc7
layer = 'fc7';

% extract training features from 3 pretrained networks
featuresTrainA = activations(net1,imdsTrainA,layer,...
    'MiniBatchSize', 1,'OutputAs','rows',...
    'ExecutionEnvironment','gpu');
featuresTrainB = activations(net2,imdsTrainV,layer,...
    'MiniBatchSize', 1,'OutputAs','rows',...
    'ExecutionEnvironment','gpu');
featuresTrainC = activations(net3,imdsTrainV,layer,...
    'MiniBatchSize', 1,'OutputAs','rows',...
    'ExecutionEnvironment','gpu');

featuresFused = max(featuresTrainA, featuresTrainB);
featuresFused = max(featuresFused, featuresTrainC);
PCAfeaturesFused = rot90(pca(featuresFused));

disp("Generating Plots");

figure;
surf(featuresFused,'EdgeColor','None');
view(3);

figure;
surf(PCAfeaturesFused,'EdgeColor','None');
view(3);

figure;
surf(featuresFused,'EdgeColor','None');

figure;
surf(PCAfeaturesFused,'EdgeColor','None');


function Iout = readAndPreprocessImageA(filename)

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

function Iout = readAndPreprocessImageV(filename)

        I = imread(filename);

        % Some images may be grayscale. Replicate the image 3 times to
        % create an RGB image.
        if ismatrix(I)
            I = cat(3,I,I,I);
        end

        % Resize the image as required for VGG.
        Iout = imresize(I, [224 224]);

        % Note that the aspect ratio is not preserved. In Caltech 101, the
        % object of interest is centered in the image and occupies a
        % majority of the image scene. Therefore, preserving the aspect
        % ratio is not critical. However, for other data sets, it may prove
        % beneficial to preserve the aspect ratio of the original image
        % when resizing.
end