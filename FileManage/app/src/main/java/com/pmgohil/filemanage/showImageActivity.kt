package com.pmgohil.filemanage

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import java.lang.Exception

class showImageActivity : AppCompatActivity() {

    lateinit var imageShow: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_image)

        if (this::imageShow.isInitialized) {
            imageShow.findViewById<ImageView>(R.id.imageShow)
            var path = intent.getStringExtra("path")
            Log.e("prakash",path.toString())
            var bitmap = BitmapFactory.decodeFile(path)
            Log.e("prakash",bitmap.toString())
            imageShow.setImageBitmap(bitmap)
        } else {
            print("prakash")
        }
    }
}