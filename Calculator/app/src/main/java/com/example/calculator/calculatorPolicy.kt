package com.example.calculator

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class calculatorPolicy : AppCompatActivity() {
    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator_policy)

        //let's create out connection manager
        val connectionManager: ConnectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        //now let's check the connection and display the information in our layout

        if (isConnected) {
            webView = findViewById<WebView>(R.id.webView)
            webView.webViewClient = WebViewClient()
            webView.loadUrl("https://pm-kishanprivacypolicy.blogspot.com/2022/01/calculator.html")
            webView.settings.javaScriptEnabled = true
            webView.settings.setSupportZoom(true)
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}