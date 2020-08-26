// Copyright (C) 2020, Nicolas Morales Escobar. All rights reserved.

package com.example.facialrecognitionanimator

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.vision.face.Landmark
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

                //Setting up paint for drawing
                val rectPaint = Paint()
                rectPaint.strokeWidth = 15f
                rectPaint.color = Color.RED
                rectPaint.style = Paint.Style.STROKE

                val bitmapOptions = BitmapFactory.Options();
                bitmapOptions.inMutable = true

                //Loading face bitmap that will be detected
                val faceBitmap = BitmapFactory.decodeResource(
                    applicationContext.resources,
                    R.drawable.test3,
                    bitmapOptions)

                //Loading and scaling moustache bitmap
                val moustacheBitmap = BitmapFactory.decodeResource(
                    applicationContext.resources,
                    R.drawable.mou1,
                    bitmapOptions)
                val scaledMoustache = Bitmap.createScaledBitmap(moustacheBitmap, 400, 200, false)

                //Loading and scaling nose bitmap
                val noseBitmap = BitmapFactory.decodeResource(
                    applicationContext.resources,
                    R.drawable.nose,
                    bitmapOptions)
                val scaledNose = Bitmap.createScaledBitmap(noseBitmap, 200, 200, false)

                //Loading and scaling eye bitmap
                val eyeBitmap = BitmapFactory.decodeResource(
                    applicationContext.resources,
                    R.drawable.eye,
                    bitmapOptions)
                val scaledEye = Bitmap.createScaledBitmap(eyeBitmap, 150, 150, false)

                //Setting up face detection
                val faceDetectorOptions = FaceDetectorOptions.Builder()
                    .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                    .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                    .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                    .build()

                val faceDetector = FaceDetection.getClient(faceDetectorOptions)

                //Setting face bitmap as InputImage
                val inputImage = InputImage.fromBitmap(faceBitmap, 0)

                val result = faceDetector.process(inputImage)
                    .addOnSuccessListener { faces ->

                        val finalBitmap = Bitmap.createBitmap(faceBitmap.width, faceBitmap.width, Bitmap.Config.ARGB_8888)
                        val canvas = Canvas(finalBitmap)

                        canvas.drawBitmap(faceBitmap, 0f, 0f, rectPaint)

                        for(face in faces)
                        {
                            val bounds = face.boundingBox
                            val rotY = face.headEulerAngleY
                            val rotZ = face.headEulerAngleZ

                            val faceDrawer = FaceDrawer(FaceHandler(face), canvas, rectPaint)

                            //Adding effects
                            faceDrawer.addEffect(MoustacheEffect(scaledMoustache))
                            faceDrawer.addEffect(NoseEffect(scaledNose))
                            faceDrawer.addEffect(EyeEffect(scaledEye))

                            faceDrawer.applyEffects()
                        }

                        //Showing final image
                        val imageView = findViewById<ImageView>(R.id.imgview)
                        imageView.setImageDrawable(BitmapDrawable(resources, finalBitmap))

                        faceDetector.close()
                    }
                    .addOnFailureListener{ e ->


                        faceDetector.close()
                    }
            }
        })
    }
}