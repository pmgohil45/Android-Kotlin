package com.example.collegeclub

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView

class privacy_policy : AppCompatActivity() {
    lateinit var webView: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        title = "Privacy Policy"
        //let's create out connection manager
        val connectionManager: ConnectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        //now let's check the connection and display the information in our layout
        var cppView = findViewById<Button>(R.id.materialViewBtn)

        if (isConnected) {
            webView = findViewById<WebView>(R.id.webView)
            webView.webViewClient = WebViewClient()
            webView.loadUrl("https://pm-kishanprivacypolicy.blogspot.com/2021/12/college-club.html")
            webView.settings.javaScriptEnabled = true
            webView.settings.setSupportZoom(true)

            var footerManage = findViewById<TextView>(R.id.footerTxt)
            footerManage.setOnClickListener {
                footerManage()
            }
        } else {
            network()
        }
    }

    fun network() {
        val network = Intent(this@privacy_policy, network::class.java)
        startActivity(network)
        finish()
    }

    fun footerManage() {
        val footer = Intent(this@privacy_policy, footerManage::class.java)
        startActivity(footer)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

}