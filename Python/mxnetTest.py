#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Thu Jul 19 09:19:41 2018

mxnet test thingy

@author: titan
"""

# We first import MXNet

import mxnet as mx

# Then we declare the data iterators to the training and validation datasets

train = mx.io.MNISTIter(
    # image      = "/media/titan/Storage 4/MNIST/train-images-idx3-ubyte",
    # label      = "/media/titan/Storage 4/MNIST/train-labels-idx1-ubyte",
    image      = 'train-images-idx3-ubyte',
    label      = 'train-labels-idx1-ubyte',
    batch_size = 128,
    data_shape = (784, ))
val   = mx.io.MNISTIter(...)

# and declare a two-layer MLP

data = mx.symbol.Variable('data')
fc1  = mx.symbol.FullyConnected(data = data, num_hidden=128)
act1 = mx.symbol.Activation(data = fc1, act_type="relu")
fc2  = mx.symbol.FullyConnected(data = act1, num_hidden = 64)
act2 = mx.symbol.Activation(data = fc2, act_type="relu")
fc3  = mx.symbol.FullyConnected(data = act2, num_hidden=10)
mlp  = mx.symbol.SoftmaxOutput(data = fc3, name = 'softmax')

# Next we train a model on the data

model = mx.model.FeedForward(
    symbol = mlp,
    num_epoch = 20,
    learning_rate = .1)
model.fit(X = train, eval_data = val)

# Finally we can predict by

test = mx.io.MNISTIter(...)
model.predict(X = test)

