// Copyright (C) 2020, Nicolas Morales Escobar. All rights reserved.

package com.example.facialrecognitionanimator

import android.graphics.Bitmap
import com.google.android.gms.vision.face.Landmark

class EyeEffect(bitmap: Bitmap, private val applyToLeftEye : Boolean = true, private val applyToRightEye: Boolean = true)
    : FaceEffect(bitmap) {

    override fun apply(faceDrawer: FaceDrawer) {

        //Based on the parameters passed as arguments in the constructor, the eye effect...
        //... will be applied to both, one or none of the eyes
        if(applyToLeftEye && applyToRightEye) {

            drawEye(faceDrawer, Landmark.LEFT_EYE)
            drawEye(faceDrawer, Landmark.RIGHT_EYE)
        }
        else if (applyToLeftEye && !applyToRightEye) {

            drawEye(faceDrawer, Landmark.LEFT_EYE)
        }
        else if (!applyToLeftEye && applyToRightEye) {

            drawEye(faceDrawer, Landmark.RIGHT_EYE)
        }
    }

    /*
     * Draws an eye in the specified landmark
     */
    private fun drawEye(faceDrawer: FaceDrawer, faceLandmark: Int) {

        val faceHandler = faceDrawer.faceHandler

        val eye = faceHandler.getLandmarkPosition(faceLandmark)

        eye.x -= bitmap.width * 0.5f
        eye.y -= bitmap.height * 0.5f

        faceDrawer.drawSprite(bitmap, eye)
    }
}