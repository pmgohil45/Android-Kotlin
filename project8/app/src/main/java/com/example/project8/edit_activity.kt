package com.example.project8

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class edit_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener(){
            finish()
        }
    }
}