package com.example.collegeclub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class welcome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val welcome = Intent(this@welcome, MainActivity::class.java)
            startActivity(welcome)
            finish()
        }, 3000)
    }
}