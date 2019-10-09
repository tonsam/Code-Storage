disp("Predictions:")
YPred = predict(classifier,featuresTestA);
accuracy = mean(YPred == YTest)

YPred = predict(classifier,featuresTestV1);
accuracy = mean(YPred == YTest)

YPred = predict(classifier,featuresTestV2);
accuracy = mean(YPred == YTest)

YPred = predict(classifier,featuresTestFused);
accuracy = mean(YPred == YTest)