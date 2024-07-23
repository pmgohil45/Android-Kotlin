package com.example.pmkisan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.kisan.MainActivity
import com.example.kisan.R
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class Welcome : AppCompatActivity() {
    lateinit var mAdView: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        // Banner ad
        var adRequest = AdRequest.Builder().build()
        mAdView = findViewById(R.id.adView)
        mAdView.loadAd(adRequest)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val welcome = Intent(this@Welcome, MainActivity::class.java)
            startActivity(welcome)
        }, 4000)
    }
}