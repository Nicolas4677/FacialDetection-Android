package com.example.facialrecognitionanimator

import android.app.AlertDialog
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.SparseArray
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.face.Face
import com.google.android.gms.vision.face.FaceDetector


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn: Button = findViewById(R.id.button)
        btn.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v : View) {

                val myImageView = findViewById<ImageView>(R.id.imgview)
                val options = BitmapFactory.Options()
                options.inMutable = true
                val myBitmap = BitmapFactory.decodeResource(
                    applicationContext.resources,
                    R.drawable.test1,
                    options
                )

                val myRectPaint = Paint()
                myRectPaint.strokeWidth = 10f
                myRectPaint.color = Color.RED
                myRectPaint.style = Paint.Style.STROKE

                val tempBitmap = Bitmap.createBitmap(
                    myBitmap.width,
                    myBitmap.height,
                    Bitmap.Config.RGB_565
                )

                val tempCanvas = Canvas(tempBitmap)
                tempCanvas.drawBitmap(myBitmap, 0f, 0f, null)

                //Tracking enabled when using video
                val faceDetector: FaceDetector =
                    FaceDetector.Builder(applicationContext)
                        .setTrackingEnabled(false)
                        .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                        .build()
                if (!faceDetector.isOperational) {
                    AlertDialog.Builder(v.context).setMessage("Could not set up the face detector!").show()
                    return
                }

                val frame: Frame = Frame.Builder().setBitmap(myBitmap).build()
                val faces: SparseArray<Face> = faceDetector.detect(frame)

                for (i in 0 until faces.size()) {

                    val face = faces.valueAt(i)

                    var landmarks = face.landmarks

                    for(i in 0 until 2)
                    {
                        val landmark = landmarks[i];
                        var position: PointF = landmark.position
                        tempCanvas.drawCircle(position.x, position.y, 15f, myRectPaint)
                    }

                    val mouthLeft = landmarks[5].position;
                    val mouthRight = landmarks[6].position;
                    val mouthMiddle = landmarks[7].position;

                    tempCanvas.drawLine(mouthLeft.x, mouthLeft.y, mouthRight.x, mouthRight.y, myRectPaint)
                    tempCanvas.drawLine(mouthRight.x, mouthRight.y, mouthMiddle.x, mouthMiddle.y, myRectPaint)
                    tempCanvas.drawLine(mouthMiddle.x, mouthMiddle.y, mouthLeft.x, mouthLeft.y, myRectPaint)
                }
                myImageView.setImageDrawable(BitmapDrawable(resources, tempBitmap))
            }
        })
    }
}

//TODO: Change API to MLKit from https://developers.google.com/ml-kit

//ORDER OF FACIAL LANDMARKS
// 0 and 1 are the eyes (0 being right eye and 1 being left eye)
// 2 being the nose
// 3 and 4 being the cheeks (3 being left cheek and 4 being right cheek)
// 5, 6, and 7 (being respectively mouth left side, mouth right side, and mouth middle bottom