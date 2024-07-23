package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        //code for return screen
        val b1 = findViewById<Button>(R.id.btn1)

        //val img2 = findViewById(R.id.image2)

        b1.setOnClickListener{
            returnActivity()
        }
    }
    fun returnActivity(){
        val intent = Intent(this@MainActivity2, MainActivity3::class.java)
        startActivity(intent);
    }
}