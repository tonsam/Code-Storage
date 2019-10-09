% Delete Files in a set of subfolders
% Cannot be undone, use with caution, etc

% set folder directory
parent = '/media/titan/Storage 1/WavH_256_ObjectCategories/';

% select subfolders
subFolders = dir(parent);
subFolders(1:2) = [];

for subFolder = subFolders'
    disp("flip")
    subPath = strcat(parent, subFolder.name, '/');
    
    files = dir(strcat(parent,subFolder.name));
    files(1:2) = [];
    
    for file = files'
        disp("flop")
        path = strcat(subPath, file.name);
        
        delete(path);
        
    end
               
end