package com.example.facialrecognitionanimator

import android.graphics.PointF
import com.google.mlkit.vision.face.Face

class FaceHandler(private val face : Face) {

    /*
     * Returns the position of the specified landmark
     */
    fun getLandmarkPosition(facialLandmark : Int) : PointF {

        val landmark = face.getLandmark(facialLandmark)

        return landmark!!.position
    }

    /*
     * Returns all the points of the specified contour
     */
    fun getContourPoints(faceContour : Int) : List<PointF> {
        val contour = face.getContour(faceContour)

        contour!!.apply {

            return points
        }
    }

    /*
     * Returns a reference to the face.
     */
    fun getFace() : Face {

        return face
    }
}