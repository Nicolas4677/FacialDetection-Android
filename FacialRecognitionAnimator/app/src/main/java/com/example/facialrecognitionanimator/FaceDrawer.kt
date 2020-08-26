// Copyright (C) 2020, Nicolas Morales Escobar. All rights reserved.

package com.example.facialrecognitionanimator

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

class FaceDrawer(val faceHandler: FaceHandler, private val canvas: Canvas,
                 private val paint: Paint)
{
    private val effectsList = mutableListOf<FaceEffect>()

    /*
     * Draws the specified landmark
     */
    fun drawFacialLandmark(facialLandmark: Int, bitmap : Bitmap? = null, offset : PointF = PointF())
    {
        val position = faceHandler.getLandmarkPosition(facialLandmark)
        position.x += offset.x
        position.y += offset.y

        if(bitmap != null)
        {
            canvas.drawBitmap(bitmap, position.x, position.y, paint)
        }
        else
        {
            canvas.drawCircle(position.x, position.y, 10f, paint)
        }
    }

    /*
     * Draws all points from a specified contour
     */
    fun drawFacialContour(faceContour: Int, bitmap : Bitmap? = null)
    {
        val points = faceHandler.getContourPoints(faceContour)

        for (point in points)
        {
            if(bitmap != null)
            {
                canvas.drawBitmap(bitmap, point.x, point.y, paint)
            }
            else
            {
                canvas.drawCircle(point.x, point.y, 10f, paint)
            }
        }
    }

    /*
     * Draws a bitmap in the canvas
     */
    fun drawSprite(bitmap: Bitmap, position : PointF) {

        canvas.drawBitmap(bitmap, position.x, position.y, paint)
    }

    /*
     * Adds effect to effects list
     */
    fun addEffect(effect : FaceEffect) {

        effectsList.add(effect)
    }

    /*
     * Applies all effects
     */
    fun applyEffects() {

        for (effect in effectsList) {

            effect.apply(this)
        }
    }
}