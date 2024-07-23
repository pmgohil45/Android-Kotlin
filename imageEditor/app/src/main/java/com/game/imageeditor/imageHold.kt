package com.game.imageeditor

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.icu.number.Scale
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.ImageView

import java.net.URI
import java.util.Collections.max
import java.util.Collections.min
import java.util.jar.Manifest
import kotlin.math.max
import kotlin.math.min

class imageHold : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var  scaleGestureDetector : ScaleGestureDetector
    private var scaleFactor = 1.0f


    companion object {
        //image pick code
        private val IMAGE_REQUEST_CODE = 100

        //permission code
        private val PERMISSION_CODE = 101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_hold)

        //open a gallery using this function
        openGalleryForImage()
        imageView = findViewById(R.id.imageView)
        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())

    }

    // Function for open gallery for image
    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent, imageHold.IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            val uri = data?.getData()
            imageView.setImageURI(uri)
        }
    }

    //funciton for the zoom image
    override fun onTouchEvent(motionEvent: MotionEvent) : Boolean {
        scaleGestureDetector.onTouchEvent(motionEvent)
        return true
    }

    //funciton for the zoom image
    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener(){
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean{
            scaleFactor *= scaleGestureDetector.scaleFactor
            scaleFactor = max(0.1f,min(scaleFactor, 10.0f))
            imageView.scaleX = scaleFactor
            imageView.scaleY = scaleFactor

            return true
        }
    }
}