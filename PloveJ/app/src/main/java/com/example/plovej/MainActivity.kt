package com.example.plovej

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        

        //code for 1st button
        val btn1 = findViewById<Button>(R.id.button1)
        btn1.setOnClickListener{
            openImgPrakash()
        }

        //code for 3rd button
        val btn3 = findViewById<Button>(R.id.button3)
        btn3.setOnClickListener{
            openImgJanki()
        }

        //code for  couple image
        val img = findViewById<ImageView>(R.id.image1)
        img.setOnClickListener{
            frag()
        }
    }
    fun openImgPrakash() {
        var prakash = Intent(this@MainActivity, prakash::class.java)
        startActivity(prakash)
    }
    fun openImgJanki() {
        var janki =Intent(this@MainActivity, janki::class.java)
        startActivity(janki)
    }
    fun frag(){
            supportFragmentManager.beginTransaction()
            .add(R.id.layout, heart_frag(),"frag")
                .addToBackStack("frag")
            .commitAllowingStateLoss()
    }
}