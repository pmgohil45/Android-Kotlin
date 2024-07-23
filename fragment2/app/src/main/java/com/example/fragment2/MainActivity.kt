package com.example.fragment2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val imageView = findViewById<ImageView>(R.id.img1)

            val btn1 = findViewById<Button>(R.id.btn1)
            btn1.setOnClickListener() {
                imageView.visibility = View.VISIBLE
            }

            val btn2 = findViewById<Button>(R.id.btn2)
            btn2.setOnClickListener() {
                imageView.visibility = View.INVISIBLE
            }
    }
}