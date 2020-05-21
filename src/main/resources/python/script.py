import sys
import numpy as np
import sklearn
import matplotlib
import keras
import pandas as pd

import matplotlib.pyplot as plt
from pandas.plotting import scatter_matrix
import seaborn as sns
from keras.models import model_from_json

print('Python: {}'.format(sys.version))
print('Numpy: {}'.format(np.__version__))
print('Pandas: {}'.format(pd.__version__))
print('Sklearn: {}'.format(sklearn.__version__))
print('Matplotlib: {}'.format(matplotlib.__version__))
print('Keras: {}'.format(keras.__version__))

# read the csv
cleveland = pd.read_csv('dataset.csv')

print( 'Shape of DataFrame: {}'.format(cleveland.shape))
print (cleveland.loc[1])
# print the last twenty or so data points
data = cleveland[~cleveland.isin(['?'])]
#data.loc[280:]
# drop rows with NaN values from DataFrame
data = data.dropna(axis=0)
#data.loc[280:]

# print the shape and data type of the dataframe
print(data.shape)
print(data.dtypes)

# transform data to numeric to enable further analysis
data = data.apply(pd.to_numeric)
data.dtypes

# print data characteristics, usings pandas built-in describe() function
data.describe()

# plot histograms for each variable
data.hist(figsize = (12, 12))
#plt.show()

X = np.array(data.drop(['corona'], 1))
y = np.array(data['corona'])


# create X and Y datasets for training
from sklearn import model_selection

X_train, X_test, y_train, y_test = model_selection.train_test_split(X, y, stratify=y, random_state=42, test_size = 0.2)

from keras.utils.np_utils import to_categorical

Y_train = to_categorical(y_train, num_classes=None)
Y_test = to_categorical(y_test, num_classes=None)
print (Y_train.shape)
print (Y_train[:10])

from keras.models import Sequential
from keras.layers import Dense
from keras.optimizers import Adam
from keras.layers import Dropout
from keras import regularizers

# define a function to build the keras model
def create_model():
    # create model
    model = Sequential()
    model.add(Dense(16, input_dim=18, kernel_initializer='normal', kernel_regularizer=regularizers.l2(0.001), activation='relu'))
    model.add(Dropout(0.25))
    model.add(Dense(8, kernel_initializer='normal', kernel_regularizer=regularizers.l2(0.001), activation='relu'))
    model.add(Dropout(0.25))
    model.add(Dense(10, activation='sigmoid'))
    
    # compile model
    adam = Adam(lr=0.001)
    model.compile(loss='binary_crossentropy', optimizer='rmsprop', metrics=['accuracy'])
    return model

model = create_model()

print(model.summary())

# fit the model to the training data
history=model.fit(X_train, Y_train, validation_data=(X_test, Y_test),epochs=50, batch_size=5)
# serialize model to JSON
model_json = model.to_json()
with open("model.json", "w") as json_file:
    json_file.write(model_json)
# serialize weights to HDF5
model.save_weights("model.h5")
#print("Saved model to disk")
