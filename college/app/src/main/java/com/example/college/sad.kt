package com.example.college

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class sad : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sad)

        supportActionBar?.hide()

        // view btn
        var cppView = findViewById<Button>(R.id.materialViewBtn)
        cppView.setOnClickListener {
            cppViewPDF()
        }
    }

    fun cppViewPDF() {
        val c = Intent(this@sad, sadViewPdf::class.java)
        startActivity(c)
    }

}
