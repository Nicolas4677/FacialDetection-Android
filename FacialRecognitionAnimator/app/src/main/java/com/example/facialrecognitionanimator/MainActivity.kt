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
                    R.drawable.test2,
                    options
                )

                val myRectPaint = Paint()
                myRectPaint.strokeWidth = 5f
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
                    FaceDetector.Builder(applicationContext).setTrackingEnabled(false)
                        .build()
                if (!faceDetector.isOperational) {
                    AlertDialog.Builder(v.context).setMessage("Could not set up the face detector!").show()
                    return
                }

                val frame: Frame = Frame.Builder().setBitmap(myBitmap).build()
                val faces: SparseArray<Face> = faceDetector.detect(frame)


                for (i in 0 until faces.size()) {

                    val thisFace = faces.valueAt(i)
                    val x1 = thisFace.position.x
                    val y1 = thisFace.position.y
                    val x2 = x1 + thisFace.width
                    val y2 = y1 + thisFace.height
                    tempCanvas.drawRoundRect(RectF(x1, y1, x2, y2), 2f, 2f, myRectPaint)
                }
                myImageView.setImageDrawable(BitmapDrawable(resources, tempBitmap))
            }
        })
    }
}