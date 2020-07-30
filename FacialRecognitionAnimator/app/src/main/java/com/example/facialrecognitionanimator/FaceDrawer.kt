// Copyright (C) 2020, Nicolas Morales Escobar. All rights reserved.

package com.example.facialrecognitionanimator

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceLandmark

class FaceDrawer(private val face : Face, private val canvas: Canvas,
                 private val paint: Paint)
{
    // * Draws the specified landmark
    fun drawFacialLandmark(facialLandmark: Int, bitmap : Bitmap? = null,
                           drawNull : Boolean = true)
    {
        val landmark = face.getLandmark(facialLandmark)

        landmark?.let {
            val position = landmark.position

            if(bitmap != null)
            {

            }
            else if(drawNull)
            {
                canvas.drawCircle(position.x, position.y, 10f, paint)
            }
        }
    }

    // * Draws all points from a specified contour.
    fun drawFacialContour(faceContour: Int)
    {
        val contour = face.getContour(faceContour)

        contour?.apply {

            for (point in points)
            {
                canvas.drawCircle(point.x, point.y, 10f, paint)
            }
        }
    }

    // * Draws all landmarks, you can pass Bitmaps for specific type of landmarks.
    // * Set drawNull Boolean as null if you want to ignore specific landmarks, you'll...
    //   ... also need to pass that landmark Bitmap as null.
    // * This method excludes the mouth because the mouth will depend on how the...
    //   ... developer wants to show it on the screen (mouth has three different landmarks).
    fun drawCompleteFace(leftEye : Bitmap? = null,
                         rightEye : Bitmap? = null,
                         leftEar : Bitmap? = null,
                         rightEar : Bitmap? = null,
                         leftCheek : Bitmap? = null,
                         rightCheek : Bitmap? = null,
                         mouth : Bitmap? = null,
                         drawNull: Boolean = true)
    {
        drawFacialLandmark(FaceLandmark.LEFT_EYE, leftEye, drawNull)
        drawFacialLandmark(FaceLandmark.RIGHT_EYE, rightEye, drawNull)
        drawFacialLandmark(FaceLandmark.LEFT_EAR, leftEar, drawNull)
        drawFacialLandmark(FaceLandmark.RIGHT_EAR, rightEar, drawNull)
        drawFacialLandmark(FaceLandmark.LEFT_CHEEK, leftCheek, drawNull)
        drawFacialLandmark(FaceLandmark.RIGHT_CHEEK, rightCheek, drawNull)
    }
}