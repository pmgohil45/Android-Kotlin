package com.example.plovej

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class prakash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prakash)

        var btn01 = findViewById<Button>(R.id.btn01)
        btn01.setOnClickListener{
            onBackPressed()
        }
    }
}