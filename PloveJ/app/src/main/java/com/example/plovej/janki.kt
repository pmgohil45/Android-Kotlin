package com.example.plovej

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class janki : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_janki)

        var btn001 = findViewById<Button>(R.id.btn001)
        btn001.setOnClickListener{
            onBackPressed()
        }
    }
}