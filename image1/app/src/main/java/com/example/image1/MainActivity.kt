package com.example.image1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imageview = findViewById<AppCompatImageView>(R.id.imageView)
        val media = "https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_960_720.jpg"
        if (media !== null) {
            Glide.with(this)
                .load(media)
                .into(imageview)
        } else {
            imageview.setImageResource(R.drawable.ic_launcher_background)
        }
    }
}