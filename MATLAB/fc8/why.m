imds = imageDatastore('/media/titan/Storage 3/2-class 3k subset/', ...
    'IncludeSubfolders',true, ...
    'LabelSource','foldernames');

tbl = countEachLabel(imds);
minSetCount = min(tbl{:,2}); % determine the smallest amount of images in a category

% Divide the data into training and validation data sets. 
% splitEachLabel splits the images datastore into two new datastores.

% Using whole dataset, trimming unneccesary
% imdsTrimmedA = imds;
% imdsTrimmedV = imds;

% Set the ImageDatastore ReadFcn and preprocess
% divide into 4 train and validation sets for Alexnets 
imds.ReadFcn = @(filename)readAndPreprocessImage1(filename);

[imdsTrain1,imdsValidation1] = splitEachLabel(imds,0.6,'randomized');
[imdsTrain2,imdsValidation2] = splitEachLabel(imds,0.6,'randomized');
[imdsTrain3,imdsValidation3] = splitEachLabel(imds,0.6,'randomized');

[imdsTrain4,imdsValidation4] = splitEachLabel(imds,0.6,'randomized');

% divide into 4 train and validation sets for VGGs 
imds.ReadFcn = @(filename)readAndPreprocessImage2(filename);

[imdsTrain5,imdsValidation5] = splitEachLabel(imds,0.6,'randomized');
[imdsTrain6,imdsValidation6] = splitEachLabel(imds,0.6,'randomized');

% --------------------------- Load Networks -----------------------------
% homogenous networks
net1 = alexnet;
net2 = alexnet;
net3 = alexnet; 

% heterogeneous networks
net4 = alexnet;
net5 = vgg16;
net6 = vgg19;

% Display the network architecture. The network has five convolutional layers and three fully connected layers.
% net.Layers

% The first layer, the image input layer, requires input images of size 227-by-227-by-3, where 3 is the number of color channels.
% inputSize = net.Layers(1).InputSize


% Replace Final Layers
% The last three layers of the pretrained networks are configured for 1000 classes. These three layers must be fine-tuned for the new classification problem. Extract all layers, except the last three, from the pretrained network.

net1Transfer = net1.Layers(1:end-3);
net2Transfer = net2.Layers(1:end-3);
net3Transfer = net3.Layers(1:end-3);
net4Transfer = net4.Layers(1:end-3);
net5Transfer = net5.Layers(1:end-3);
net6Transfer = net6.Layers(1:end-3);

% Transfer the layers to the new classification task by replacing the last three layers with a fully connected layer, a softmax layer, and a classification output layer. Specify the options of the new fully connected layer according to the new data. Set the fully connected layer to have the same size as the number of classes in the new data. To learn faster in the new layers than in the transferred layers, increase the WeightLearnRateFactor and BiasLearnRateFactor values of the fully connected layer.
% 'WeightLearnRateFactor',20,'BiasLearnRateFactor',20
numClasses = numel(categories(imdsTrain1.Labels))


HomAlexnet1 = [
    net1Transfer
    fullyConnectedLayer(numClasses)
    softmaxLayer
    classificationLayer];

HomAlexnet2 = [
    net2Transfer
    fullyConnectedLayer(numClasses)
    softmaxLayer
    classificationLayer];

HomAlexnet3 = [
    net3Transfer
    fullyConnectedLayer(numClasses)
    softmaxLayer
    classificationLayer];

HetAlexnet = [
    net4Transfer
    fullyConnectedLayer(numClasses)
    softmaxLayer
    classificationLayer];

HetVGG16 = [
    net5Transfer
    fullyConnectedLayer(numClasses)
    softmaxLayer
    classificationLayer];

HetVGG19 = [
    net6Transfer
    fullyConnectedLayer(numClasses)
    softmaxLayer
    classificationLayer];

% Specify the training options. 
% For transfer learning, keep the features from the early layers of the pretrained network (the transferred layer weights). To slow down learning in the transferred layers, set the initial learning rate to a small value. In the previous step, you increased the learning rate factors for the fully connected layer to speed up learning in the new final layers. This combination of learning rate settings results in fast learning only in the new layers and slower learning in the other layers. When performing transfer learning, you do not need to train for as many epochs. An epoch is a full training cycle on the entire training data set. Specify the mini-batch size and validation data. The software validates the network every ValidationFrequency iterations during training.

options = trainingOptions('sgdm', ...
    'MiniBatchSize',32, ...
    'MaxEpochs',3, ...
    'InitialLearnRate',1e-3, ...
    'ExecutionEnvironment','multi-gpu');
    %'Plots','training-progress',...
    %'ValidationData',augimdsValidation, ...
    %'ValidationFrequency',3, ...
    %'ValidationPatience',Inf, ...
    %'Verbose',false, ...
    

% Train the network that consists of the transferred and new layers.
% unless the networks are already trained, in which case skip it
if exist('trainedHet3','var') == 0
    
    trainedHom1 = trainNetwork(imdsTrain1,HomAlexnet1,options);
    trainedHom2 = trainNetwork(imdsTrain2,HomAlexnet2,options);
    trainedHom3 = trainNetwork(imdsTrain3,HomAlexnet3,options);
    trainedHet1 = trainNetwork(imdsTrain4,HetAlexnet,options);
    trainedHet2 = trainNetwork(imdsTrain5,HetVGG16,options);
    trainedHet3 = trainNetwork(imdsTrain6,HetVGG19,options);
    
end

% Classify Validation Images

% Classify the validation images using the fine-tuned network.
% [YPred,scores] = classify(netTransfer,imdsValidation);

% Calculate the classification accuracy on the validation set. 
% Accuracy is the fraction of labels that the network predicts correctly.

% YValidation = imdsValidation.Labels;
% accuracy = mean(YPred == YValidation)

% and now... features! 
layer = 'fc';

% extract features from training sets for each nyetwork
disp('Extracting Features...');


% loop through imdsTrain1 and extract features from each individual file
for i = 1:length(imdsTrain1.Files)
    
    imName = imdsTrain1.Files(i);
    im = imread(imName{1});
    
    features1 = activations(trainedHom1,im,layer,...
        'MiniBatchSize', 1,'OutputAs','rows',...
        'ExecutionEnvironment','gpu');
    
    pca1 = pca(features1);
    tsne1 = tsne(features1);
    
end



% features2 = activations(trainedHom2,imdsTrain2,layer,...
%     'MiniBatchSize', 1,'OutputAs','rows',...
%     'ExecutionEnvironment','gpu');
% features3 = activations(trainedHom3,imdsTrain3,layer,...
%     'MiniBatchSize', 1,'OutputAs','rows',...
%     'ExecutionEnvironment','gpu');
% features4 = activations(trainedHet1,imdsTrain4,layer,...
%     'MiniBatchSize', 1,'OutputAs','rows',...
%     'ExecutionEnvironment','gpu');
% features5 = activations(trainedHet2,imdsTrain5,layer,...
%     'MiniBatchSize', 1,'OutputAs','rows',...
%     'ExecutionEnvironment','gpu');
% features6 = activations(trainedHet3,imdsTrain6,layer,...
%     'MiniBatchSize', 1,'OutputAs','rows',...
%     'ExecutionEnvironment','gpu');

% apply PCA
pca1 = pca(features1);
pca2 = pca(features2);
pca3 = pca(features3);
pca4 = pca(features4);
pca5 = pca(features5);
pca6 = pca(features6);

figure('Name','Transfer Alexnet Training Features');
surf(pca1,'EdgeColor','none');
figure('Name','Transfer Alexnet Test Features');
surf(pca2,'EdgeColor','none');

tis1 = tsne(featuresTest);
figure('Name','t-SNE Scatter Plot (Alexnet)');
gscatter(tis1(:,1),tis1(:,2),YValidation);

% image input function
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

% image input function
function Iout = readAndPreprocessImage2(filename)

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
