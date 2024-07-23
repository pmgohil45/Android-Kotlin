package com.pmgohil.calldemo

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var dialET: EditText
    lateinit var dialPadImg : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dialPadImg = findViewById<ImageView>(R.id.dialPadImg)
        dialPadImg.setOnClickListener(){
            dialET = findViewById<EditText>(R.id.dialET)
            dialET.visibility = VISIBLE
        }
    }
}