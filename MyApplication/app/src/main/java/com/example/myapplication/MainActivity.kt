package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var txt = findViewById<TextView>(R.id.txt)


        var b1 = findViewById<Button>(R.id.btn1)

         b1.setOnClickListener {
            txt.text = "app start now..."
        }

        var b2 = findViewById<Button>(R.id.btn2)

        b2.setOnClickListener {
            txt.setTextColor(Color.parseColor("#ff0000"))


        }

        var b3 = findViewById<Button>(R.id.btn3)

        b3.setOnClickListener{
            txt.setTextSize(80f)
        }
// code for image view


        var b4 = findViewById<Button>(R.id.btn4)

        b4.setOnClickListener{
            openActivity2()
        }
   }

    fun openActivity2() {
        val intent = Intent(this@MainActivity, MainActivity2::class.java)
        startActivity(intent);
    }

}