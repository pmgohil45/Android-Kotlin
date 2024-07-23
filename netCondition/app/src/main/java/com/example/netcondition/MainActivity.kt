package com.example.netcondition

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textview)
        val btn = findViewById<Button>(R.id.btn)

        //let's create out connection manager
        val connectionManager: ConnectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true

        //now let's check the connection and display the information in our layout
        if (isConnected) {
            val btn2 = findViewById<Button>(R.id.btn2)
            btn2.setOnClickListener {
                activity2()
            }
            val btn = findViewById<Button>(R.id.btn)
            btn.setOnClickListener {
                activity3()
            }
        } else {
            textView.text = "Error"
            finish()
        }

        //now let's create the button event function
        // bellow code is click on check network on or off
/*        btn.setOnClickListener {
            val activeNetwork: NetworkInfo? = connectionManager.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
            //now let's check the connection and display the information in our layout
            if (isConnected) {
                textView.text = "connected"
            } else {
                textView.text = "Error"
            }
        }*/
    }


    fun activity2() {
        val c = Intent(this@MainActivity, Activity2::class.java)
        startActivity(c)
    }

    fun activity3() {
        val c = Intent(this@MainActivity, Activity3::class.java)
        startActivity(c)
    }
}