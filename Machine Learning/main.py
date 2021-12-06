# -*- coding: utf-8 -*-
"""
Created on Thu Jan 28 00:44:25 2021

@author: chakati
"""
import cv2
import numpy as np
import os
import tensorflow as tf

from handshape_feature_extractor import HandShapeFeatureExtractor
from frameextractor import frameExtractor

#ex===
import glob
import scipy.spatial
import shutil
from numpy import genfromtxt
#===

def extractPL(frames_dir, csv_file):
    vectors = []
    videos = []
    frameBucket = os.path.join(frames_dir, "*.png")
    frames = sorted(glob.glob(frameBucket))
    #frames = os.listdir(path)
    cnn_model = HandShapeFeatureExtractor.get_instance()
    for framex in frames:
        frame = cv2.imread(framex)
        frame = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
        vector = cnn_model.extract_feature(frame)
        vectors.append(np.squeeze(vector))
        videos.append(os.path.basename(framex))

    np.savetxt(csv_file, vectors, delimiter=",")

# =============================================================================
# Get the penultimate layer for test data
# =============================================================================

video_dir = os.path.join('test')
videos = os.path.join(video_dir, "*.mp4")

#videos = os.listdir(video_path)
videos = sorted(glob.glob(videos)) #wildcard

j = 0 #1
frames_dir = os.path.join(os.getcwd(), "testf")
for video in videos:
    frameExtractor(video, frames_dir, j)
    j = j + 1

test_csv = 'testdata.csv'
extractPL(frames_dir, test_csv)
test_data = genfromtxt(test_csv, delimiter=",")
# =============================================================================
# Get the penultimate layer for training data
# =============================================================================


video_dir = os.path.join('traindata')
videos = os.path.join(video_dir, "*.mp4")

#videos = os.listdir(video_path)
videos = sorted(glob.glob(videos)) #wildcard

i = 0 #1
frames_dir = os.path.join(os.getcwd(), "trainf")
for video in videos:
    frameExtractor(video, frames_dir, i)
    i = i + 1

train_csv = 'traindata.csv'
extractPL(frames_dir, train_csv)
training_data = genfromtxt(train_csv, delimiter=",")

def calcGest(test, train):
    gesList = []
    for i in train:
        gesList.append(scipy.spatial.distance.cosine(test, i))
    return gesList.index(min(gesList))%17

#===res
result_csv = []
for k in test_data:
    result_csv.append(calcGest(k, training_data))

#print(result_csv)
np.savetxt("Results.csv", result_csv, delimiter=",", fmt='% d')
