% this code will be used for reading input data from either the imagenet
% dataset or from the list of urls.

% set list location
urlListLocation = '/media/titan/Storage 3/ImageNet/imagenet_fall11_urls/fall11_urls.txt'

% read in the list
urlList = fileread(urlListLocation);

% set number of images to load from list
numImages = 1

% create imagedatastore for images


for x = 1:numImages
   urlread(x) 
end