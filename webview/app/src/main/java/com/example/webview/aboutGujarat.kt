package com.example.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class aboutGujarat : AppCompatActivity() {
    lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_gujarat)

        title = "About Gujarat"

        // WebViewClient allows you to handle
        // onPageFinished and override Url loading. val webView = findViewById<WebView>(R.id.webView)
        webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()

        // this will load the url of the website
        webView.loadUrl("https://gujaratindia.gov.in/about-gujarat/gujarat-at-glance.htm")


        // this will enable the javascript settings
        webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        webView.settings.setSupportZoom(true)

    }

    override fun onBackPressed() {
        // if your webview can go back it will go back
        if (webView.canGoBack()) {
            webView.goBack()
            // if your webview cannot go back
            // it will exit the application

        } else {
            super.onBackPressed()
        }
    }
}