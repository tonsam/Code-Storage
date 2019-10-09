# -*- coding: utf-8 -*-

import sys
sys.path = ['', '/home/titan/anaconda3/envs/tensorflow/lib/python36.zip', '/home/titan/anaconda3/envs/tensorflow/lib/python3.6', '/home/titan/anaconda3/envs/tensorflow/lib/python3.6/lib-dynload', '/home/titan/anaconda3/envs/tensorflow/lib/python3.6/site-packages']



import numpy as np
np.random.seed(123)  # for reproducibility

from keras.models import Sequential

from keras.layers import Dense, Dropout, Activation, Flatten

from keras.layers import Convolution2D, MaxPooling2D

from keras.utils import np_utils

from keras.datasets import mnist

# Load pre-shuffled MNIST data into train and test sets
(X_train, y_train), (X_test, y_test) = mnist.load_data()

print(X_train.shape)
# (60000, 28, 28)

from matplotlib import pyplot as plt
plt.imshow(X_train[0])
