imds = imageDatastore('/media/titan/Storage 3/ImageNet/ILSVRC2014_DET_train/categories', ...
    'IncludeSubfolders',true, ...
    'LabelSource','foldernames');

tbl = countEachLabel(imds);
minSetCount = min(tbl{:,2}); % determine the smallest amount of images in a category

% Divide the data into training and validation data sets. 
% splitEachLabel splits the images datastore into two new datastores.

% Using whole dataaset, trimming unneccesary
imdsTrimmed = imds

% Set the ImageDatastore ReadFcn and preprocess
imdsTrimmed.ReadFcn = @(filename)readAndPreprocessImage1(filename);

% divide into test and validation sets
[imdsTrain,imdsValidation] = splitEachLabel(imdsTrimmed,0.7,'randomized');

numTrainImages = numel(imdsTrain.Labels);
idx = randperm(numTrainImages,16);
figure
for i = 1:16
    subplot(4,4,i)
    I = readimage(imdsTrain,idx(i));
    imshow(I)
end

% Load Pretrained Network

% Load the pretrained AlexNet neural network. If Neural Network Toolbox™ Model for AlexNet Network is not installed, then the software provides a download link. AlexNet is trained on more than one million images and can classify images into 1000 object categories, such as keyboard, mouse, pencil, and many animals. As a result, the model has learned rich feature representations for a wide range of images.

net = alexnet;

% Display the network architecture. The network has five convolutional layers and three fully connected layers.

net.Layers

% The first layer, the image input layer, requires input images of size 227-by-227-by-3, where 3 is the number of color channels.

inputSize = net.Layers(1).InputSize


% Replace Final Layers

% The last three layers of the pretrained network net are configured for 1000 classes. These three layers must be fine-tuned for the new classification problem. Extract all layers, except the last three, from the pretrained network.

layersTransfer = net.Layers(1:end-3);

% Transfer the layers to the new classification task by replacing the last three layers with a fully connected layer, a softmax layer, and a classification output layer. Specify the options of the new fully connected layer according to the new data. Set the fully connected layer to have the same size as the number of classes in the new data. To learn faster in the new layers than in the transferred layers, increase the WeightLearnRateFactor and BiasLearnRateFactor values of the fully connected layer.
% 'WeightLearnRateFactor',20,'BiasLearnRateFactor',20
numClasses = numel(categories(imdsTrain.Labels))

layers = [
    layersTransfer
    fullyConnectedLayer(numClasses)
    softmaxLayer
    classificationLayer];

% Specify the training options. 
% For transfer learning, keep the features from the early layers of the pretrained network (the transferred layer weights). To slow down learning in the transferred layers, set the initial learning rate to a small value. In the previous step, you increased the learning rate factors for the fully connected layer to speed up learning in the new final layers. This combination of learning rate settings results in fast learning only in the new layers and slower learning in the other layers. When performing transfer learning, you do not need to train for as many epochs. An epoch is a full training cycle on the entire training data set. Specify the mini-batch size and validation data. The software validates the network every ValidationFrequency iterations during training.

options = trainingOptions('sgdm', ...
    'MiniBatchSize',50, ...
    'MaxEpochs',50, ...
    'InitialLearnRate',1e-3, ...
    'Plots','training-progress',...
    'ExecutionEnvironment','multi-gpu');
    %'ValidationData',augimdsValidation, ...
    %'ValidationFrequency',3, ...
    %'ValidationPatience',Inf, ...
    %'Verbose',false, ...
    

% Train the network that consists of the transferred and new layers. 

netTransfer = trainNetwork(imdsTrain,layers,options);

% Classify Validation Images

% Classify the validation images using the fine-tuned network.

[YPred,scores] = classify(netTransfer,imdsValidation);

% Display four sample validation images with their predicted labels.

idx = randperm(numel(imdsValidation.Files),4);
figure
for i = 1:4
    subplot(2,2,i)
    I = readimage(imdsValidation,idx(i));
    imshow(I)
    label = YPred(idx(i));
    title(string(label));
end

% Calculate the classification accuracy on the validation set. 
% Accuracy is the fraction of labels that the network predicts correctly.

YValidation = imdsValidation.Labels;
accuracy = mean(YPred == YValidation)



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
