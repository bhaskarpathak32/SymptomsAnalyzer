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


# load json and create model
json_file = open('model.json', 'r')
loaded_model_json = json_file.read()
json_file.close()
loaded_model = model_from_json(loaded_model_json)
# load weights into new model
loaded_model.load_weights("model.h5")
#print("Loaded model from disk")
np.set_printoptions(formatter={'float': lambda x: "{0:0.3f}".format(x)})
loaded_model.compile(loss='binary_crossentropy', optimizer='rmsprop', metrics=['accuracy'])
dddd=pd.read_csv(sys.argv[1]+'_query.csv')
Z = np.array(dddd)
pred = loaded_model.predict(Z);
print(pred)
arg=sys.argv[1]
ext='.dat'
np.savetxt(arg+ext,pred,fmt='%.3f', delimiter=",")