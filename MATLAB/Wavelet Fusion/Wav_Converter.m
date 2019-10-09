% convert hex to binary

% set folder directory
parent = '/media/titan/Storage 1/256_ObjectCategories/';

% adjust to path based on which wavelet part you are using
to = '/media/titan/Storage 1/WavD_256_ObjectCategories/';

% select subfolders
subFolders = dir(parent);
subFolders(1:2) = [];

for subFolder = subFolders'
    disp("flip")
    subPath = strcat(parent, subFolder.name, '/');
    subTo = strcat(to, subFolder.name, '/');
    
    files = dir(strcat(parent,subFolder.name));
    files(1:2) = [];
    
    for file = files'
        disp("flop")
        path = strcat(subPath, file.name);
        toPath = strcat(subTo, 'D_', file.name);
        
        img = imread(path);
        
        filename = strcat(toPath,'.jpeg');
        
        [ignore,cH,cV,cD] = dwt2(img,'sym4','mode','per');
        
        % comment out the parts you don't need
        
        % Horizontal Detail Coefficient
        %imwrite(cH, filename);
        
        % Vertical Detail Coefficient
        % imwrite(cV, filename);
        
        % Diagonal Detail Coefficient
        imwrite(cD, filename);
        
    end
               
end