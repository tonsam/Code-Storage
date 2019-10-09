tic();
imds = imageDatastore('/media/titan/Storage 1/256_ObjectCategories', 'IncludeSubfolders', true, ...
    'LabelSource', 'foldernames');

tbl = countEachLabel(imds)

% minSetCount = min(tbl{:,2}); % determine the smallest amount of images in a category
minSetCount = 40

% Use splitEachLabel method to trim the set.
imds1 = splitEachLabel(imds, minSetCount, 'randomize');

% Notice that each set now has exactly the same number of images.
countEachLabel(imds)
imds2 = splitEachLabel(imds, minSetCount, 'randomize');


% Load pre-trained AlexNet
net1 = alexnet();

% View the CNN architecture
net1.Layers;


% Load pre-trained VGG19
net2 = vgg16();

net2.Layers

% Number of class names for ImageNet classification task
numel(net1.Layers(end).ClassNames)

% Set the ImageDatastore ReadFcn
imds1.ReadFcn = @(filename)readAndPreprocessImage1(filename);

[trainingSet1, testSet1] = splitEachLabel(imds1, .6, 'randomize');

% Number of class names for ImageNet classification task
numel(net2.Layers(end).ClassNames)

% Set the ImageDatastore ReadFcn
imds2.ReadFcn = @(filename)readAndPreprocessImage2(filename);

[trainingSet2, testSet2] = splitEachLabel(imds2, .6, 'randomize');

% Get the network weights for the second convolutional layer
w1 = net1.Layers(2).Weights;

% Scale and resize the weights for visualization
w1 = mat2gray(w1);
w1 = imresize(w1,5);

% Display a montage of network weights. There are 96 individual sets of
% weights in the first layer.
figure
montage(w1)
title('First convolutional layer weights')

% Get the network weights for the second convolutional layer
w2 = net2.Layers(2).Weights;

% Scale and resize the weights for visualization
w2 = mat2gray(w2);
w2 = imresize(w2,5);

% Display a montage of network weights. There are 96 individual sets of
% weights in the first layer.
figure
montage(w2)
title('First convolutional layer weights')

featureLayer = 'fc7';
trainingFeatures1 = activations(net1, trainingSet1, featureLayer, ...
    'MiniBatchSize', 32, 'OutputAs', 'rows',...
    'ExecutionEnvironment','gpu');
trainingFeatures1;

featureLayer = 'fc7';
trainingFeatures2 = activations(net2, trainingSet2, featureLayer, ...
    'MiniBatchSize', 32, 'OutputAs', 'rows',...
    'ExecutionEnvironment','gpu');
trainingFeatures2;

fusionFeatures = trainingFeatures1 + trainingFeatures2;
fusionFeatures;

% Get training labels from the first trainingSet
trainingLabels = trainingSet1.Labels;

% Train multiclass SVM classifier using a fast linear solver, and set
% 'ObservationsIn' to 'columns' to match the arrangement used for training
% features.
classifier = fitcecoc(fusionFeatures, trainingLabels, ...
    'Learners', 'Linear', 'Coding', 'onevsall', 'ObservationsIn', 'rows');

% Extract test features using the CNN
testFeatures = activations(net1, imds1, featureLayer, 'MiniBatchSize',32,...
    'OutputAs', 'rows',...
    'ExecutionEnvironment','gpu');

% Pass CNN image features to trained classifier
predictedLabels = predict(classifier, testFeatures);

% Get the known labels
testLabels = imds1.Labels;

% Tabulate the results using a confusion matrix.
confMat = confusionmat(testLabels, predictedLabels);

% Convert confusion matrix into percentage form
confMat = bsxfun(@rdivide,confMat,sum(confMat,2));

% Display the mean accuracy
mean(diag(confMat))
toc()

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

function Iout = readAndPreprocessImage2(filename)

        I = imread(filename);

        % Some images may be grayscale. Replicate the image 3 times to
        % create an RGB image.
        if ismatrix(I)
            I = cat(3,I,I,I);
        end

        % Resize the image as required for VGG19.
        Iout = imresize(I, [224 224]);

        % Note that the aspect ratio is not preserved. In Caltech 101, the
        % object of interest is centered in the image and occupies a
        % majority of the image scene. Therefore, preserving the aspect
        % ratio is not critical. However, for other data sets, it may prove
        % beneficial to preserve the aspect ratio of the original image
        % when resizing.
end
