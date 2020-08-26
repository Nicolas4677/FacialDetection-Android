// Copyright (C) 2020, Nicolas Morales Escobar. All rights reserved.

package com.example.facialrecognitionanimator

import android.graphics.Bitmap

/*
 * Parent class of all Face Effects
 */
abstract class FaceEffect(protected val bitmap: Bitmap) {

    /*
     * The implementation of this method depends on each children of this class.
     */
    abstract fun apply (faceDrawer: FaceDrawer)
}