// Copyright (C) 2020, Nicolas Morales Escobar. All rights reserved.

package com.example.facialrecognitionanimator

import android.graphics.Bitmap
import android.graphics.PointF
import com.google.mlkit.vision.face.FaceLandmark

class MoustacheEffect(bitmap: Bitmap) : FaceEffect(bitmap) {

    /*
     * Draws a bitmap in the middle position between mouth and nose
     */
    override fun apply(faceDrawer: FaceDrawer) {

        val faceHandler = faceDrawer.faceHandler

        val nose = faceHandler.getLandmarkPosition(FaceLandmark.NOSE_BASE)
        val mouth = faceHandler.getLandmarkPosition(FaceLandmark.MOUTH_BOTTOM)


        //Finding middle point between the nose and the bottom of the mouth
        val moustachePosition = PointF((mouth.x + nose.x) * 0.5f, (mouth.y + nose.y) * 0.5f)

        //This sets the bitmap center to be in the moustachePosition by removing the offset from the top left
        moustachePosition.x -= bitmap.width * 0.5f
        moustachePosition.y -= bitmap.height * 0.5f

        faceDrawer.drawSprite(bitmap, moustachePosition)
    }
}