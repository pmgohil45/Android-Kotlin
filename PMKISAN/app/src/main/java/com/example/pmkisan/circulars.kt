package com.example.pmkisan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView

class circulars : AppCompatActivity() {
    lateinit var webView: WebView
    lateinit var mAdView: AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circulars)
        // Banner ad
        var adRequest = AdRequest.Builder().build()
        mAdView = findViewById(R.id.adView)
        mAdView.loadAd(adRequest)

        title = "Circulars"

        webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.loadUrl("https://pmkisan.gov.in/LetterandCirculars.aspx")
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}