% this code will be used for reading input data from either the imagenet
% dataset or from the list of urls.

% set file location
FilesLocation = '/media/titan/Storage 3/ImageNet/ILSVRC2017_DET/ILSVRC/Data/DET/train'

% create datastore from files
imds = imageDatastore(FilesLocation,'IncludeSubfolders',true);


