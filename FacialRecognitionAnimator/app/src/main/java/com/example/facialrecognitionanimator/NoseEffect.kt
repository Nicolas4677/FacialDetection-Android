// Copyright (C) 2020, Nicolas Morales Escobar. All rights reserved.

package com.example.facialrecognitionanimator

import android.graphics.Bitmap
import com.google.mlkit.vision.face.FaceLandmark

class NoseEffect(bitmap: Bitmap) : FaceEffect(bitmap) {

    /*
     * Draws nose bitmap in the position of the nose
     */
    override fun apply(faceDrawer: FaceDrawer) {

        val faceHandler = faceDrawer.faceHandler

        val nose = faceHandler.getLandmarkPosition(FaceLandmark.NOSE_BASE)
        nose.x -= bitmap.width * 0.5f
        nose.y -= bitmap.height * 0.5f

        faceDrawer.drawSprite(bitmap, nose)
    }
}