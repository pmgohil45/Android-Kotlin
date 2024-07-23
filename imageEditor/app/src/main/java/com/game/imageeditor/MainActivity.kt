package com.game.imageeditor

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    private lateinit var imageCardView: CardView
    private lateinit var cameraCard: CardView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageCardView = findViewById(R.id.imageCard)
        imageCardView.setOnClickListener()
        {
            callTheImageHold()
        }


        cameraCard = findViewById(R.id.cameraCard)
        cameraCard.setOnClickListener() {
            openCameraForTackPhoto()
        }
    }


    private fun callTheImageHold() {
        val intent = Intent(this, imageHold::class.java)
        startActivity(intent)
    }

    // Function for open camera for tack a photo

    val REQUEST_IMAGE_CAPTURE = 1

    private fun openCameraForTackPhoto() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

}