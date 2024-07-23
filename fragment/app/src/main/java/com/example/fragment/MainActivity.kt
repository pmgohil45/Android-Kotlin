package com.example.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btn = findViewById<Button>(R.id.button1)
        btn.setOnClickListener(){
            fragment()
        }

    }

    fun fragment(){
            supportFragmentManager.beginTransaction()
            .add(R.id.layout, Fragment1(), "fragment")
            .commitAllowingStateLoss()
    }
}