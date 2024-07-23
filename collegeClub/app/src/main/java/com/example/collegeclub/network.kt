package com.example.collegeclub

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class network : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)

        val retryBtn = findViewById<Button>(R.id.retry)
        retryBtn.setOnClickListener() {
            //let's create out connection manager
            val connectionManager: ConnectivityManager =
                this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
            //now let's check the connection and display the information in our layout
            if (isConnected) {
                gotoHomePage()
            } else {
                Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun gotoHomePage() {
        val m = Intent(this@network, MainActivity::class.java)
        startActivity(m)
        setResult(2, intent)
        finish()
    }
}