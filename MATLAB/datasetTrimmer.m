% quick and dirty script to trim folders from a parent directory based on
% item contents.

d = '/media/titan/Storage 3/ImageNet full/';
% d = '/home/titan/Desktop/test/';

%set the minimum number of items to avoid deletion
threshold = 100;
foldersDeleted = 0;

files = dir(d);
files(1:2) = [];

for file = files'
    f = strcat(d,file.name);
    if numel(dir(f))-2 < threshold
        rmdir(f, 's');
        foldersDeleted=foldersDeleted+1;
    end
           
end

disp(strcat('Folders Removed: ', int2str(foldersDeleted)));