package com.example.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class mapp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapp)

        title = "Pm Maps"
        // WebViewClient allows you to handle
        // onPageFinished and override Url loading. val webView = findViewById<WebView>(R.id.webView)
        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()

        // this will load the url of the website
        webView.loadUrl("https://www.google.com/maps/")


        // this will enable the javascript settings
        webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        webView.settings.setSupportZoom(true)

        // if you press Back button this code will work
        fun onBackPressed() {
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
}