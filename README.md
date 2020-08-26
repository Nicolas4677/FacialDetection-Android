FacialRecognition_Android

An Android Studio project which uses the MLKit Vision API(https://developers.google.com/ml-kit/vision/face-detection) to detect faces and apply different effects.

The effects are easilly modifiable and extendible, there's a FaceEffect class which is the parent of all Effects applied to the face. If you want to add more effects just inherit from that class and override the apply method.
