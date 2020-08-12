// Copyright (C) 2020, Nicolas Morales Escobar. All rights reserved.

package com.example.facialrecognitionanimator

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.vision.face.Contour
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn: Button = findViewById(R.id.button)
        btn.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v : View) {

                val bitmapOptions = BitmapFactory.Options();
                bitmapOptions.inMutable = true

                val imageBitmap = BitmapFactory.decodeResource(
                    applicationContext.resources,
                    R.drawable.test3,
                    bitmapOptions)

                val rectPaint = Paint()
                rectPaint.strokeWidth = 15f
                rectPaint.color = Color.RED
                rectPaint.style = Paint.Style.STROKE

                val canvas = Canvas(imageBitmap)
                canvas.drawBitmap(imageBitmap, 0f, 0f, null)

                var faceDetectorOptions = FaceDetectorOptions.Builder()
                    .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                    .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                    .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                    .build()

                val inputImage = InputImage.fromBitmap(imageBitmap, 0)

                val faceDetector = FaceDetection.getClient(faceDetectorOptions)

                val result = faceDetector.process(inputImage)
                    .addOnSuccessListener { faces ->

                        for(face in faces)
                        {
                            val bounds = face.boundingBox
                            val rotY = face.headEulerAngleY
                            val rotZ = face.headEulerAngleZ

                            val faceDrawer = FaceDrawer(face, canvas, rectPaint)

                            faceDrawer.drawCompleteFace()

                            rectPaint.color = Color.BLUE

                            faceDrawer.drawFacialContour(FaceContour.FACE)
                            faceDrawer.drawFacialContour(FaceContour.LEFT_EYE)
                            faceDrawer.drawFacialContour(FaceContour.RIGHT_EYE)
                            faceDrawer.drawFacialContour(FaceContour.LOWER_LIP_BOTTOM)
                            faceDrawer.drawFacialContour(FaceContour.LOWER_LIP_TOP)
                            faceDrawer.drawFacialContour(FaceContour.UPPER_LIP_BOTTOM)
                            faceDrawer.drawFacialContour(FaceContour.UPPER_LIP_TOP)
                        }

                        val imageView = findViewById<ImageView>(R.id.imgview)
                        imageView.setImageDrawable(BitmapDrawable(resources, imageBitmap))
                    }
                    .addOnFailureListener{ e ->

                    }
            }
        })
    }
}

//TODO: Change API to MLKit from https://developers.google.com/ml-kit
//MLKit Vision API
//To detect the contours of the face, MLKit requires an image of at least 200x200 Pixels.
//Input image can be:  Bitmap, media.Image, ByteBuffer, byte array, or a file on the device.

//Mobile Vision API
//ORDER OF FACIAL LANDMARKS
// 0 and 1 are the eyes (0 being right eye and 1 being left eye)
// 2 being the nose
// 3 and 4 being the cheeks (3 being left cheek and 4 being right cheek)
// 5, 6, and 7 (being respectively mouth left side, mouth right side, and mouth middle bottom