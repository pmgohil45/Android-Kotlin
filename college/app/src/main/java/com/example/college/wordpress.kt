package com.example.college

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class wordpress : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wordpress)

        supportActionBar?.hide()

        // view btn
        var cppView = findViewById<Button>(R.id.materialViewBtn)
        cppView.setOnClickListener {
            cppViewPDF()
        }
    }

    fun cppViewPDF() {
        val c = Intent(this@wordpress, wordpressViewPdf::class.java)
        startActivity(c)
    }

}