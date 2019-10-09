clear all;
% first experiment with autoencoders and dcnn fusion

imds = imageDatastore('/media/titan/Storage 1/256_ObjectCategories',...
    'IncludeSubfolders', true, ...
    'LabelSource', 'foldernames');

numImgs = 40;
imdsTrimmed = splitEachLabel(imds, numImgs, 'randomized');
imdsTrimmed2 = imdsTrimmed;
% Alexnet and vgg16/19 use different sized input images.
% Use splitEachLabel method to trim two sets, for Alexnet and VGG based nets.
imdsTrimmed.ReadFcn = @(filename)readAndPreprocessImageA(filename);
[imdsTrainA,imdsTestA] = splitEachLabel(imdsTrimmed,0.6,'randomized');

imdsTrimmed2.ReadFcn = @(filename)readAndPreprocessImageV(filename);
[imdsTrainV,imdsTestV] = splitEachLabel(imdsTrimmed2,0.6,'randomized');
% autoencoder stuff

disp("Converting Train Data");
XTrain = cell(imdsTrainA.readall)';


disp("Training Autoencoder");
hiddenSize = 1;

autoenc = trainAutoencoder(XTrain,hiddenSize,...
        'L2WeightRegularization',0.004,...
        'SparsityRegularization',4,...
        'SparsityProportion',0.15,...
        'UseGPU', 1,...
        'MaxEpoch',500);
    
disp("Converting Test Data");


XTest = cell(imdsTestA.readall)';

xReconstructed = predict(autoenc,XTest);

error=0;

for a = 1:1:5000
    error = error + mse(XTrain{a}-xReconstructed{a});
end

error = error/5000;

figure;
for i = 1:20
    subplot(4,5,i);
    imshow(XTest{i});
end

figure;
for i = 1:20
    subplot(4,5,i);
    imshow(xReconstructed{i});
end

disp("Average Mean Squared Error: " + error);



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