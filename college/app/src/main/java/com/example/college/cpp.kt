package com.example.college

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class cpp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cpp)

        supportActionBar?.hide()

        // view btn
        var cppView = findViewById<Button>(R.id.materialViewBtn)
        cppView.setOnClickListener {
            cppViewPDF()
        }
    }

    fun cppViewPDF() {
        val c = Intent(this@cpp, cppViewPdf::class.java)
        startActivity(c)
    }

}