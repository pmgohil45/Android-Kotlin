package com.example.college

import android.app.Notification
import android.content.ContentProvider
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    /*   private lateinit var networkConnectionClass: networkConnection
       private lateinit var networkImg: ImageView*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //check net connection
        /*checkNetworkConnection()

        networkImg = findViewById(R.id.networkImg)
        */
        //hide action bar
        supportActionBar?.hide()

        var sad = findViewById<Button>(R.id.subBtn1)
        sad.setOnClickListener {
            sad()
        }

        var cpp = findViewById<Button>(R.id.subBtn2)
        cpp.setOnClickListener {
            cpp()
        }

        var oracle = findViewById<Button>(R.id.subBtn3)
        oracle.setOnClickListener {
            oracle()
        }

        var wordpress = findViewById<Button>(R.id.subBtn4)
        wordpress.setOnClickListener {
            wordpress()
        }
    }

    fun sad() {
        val s = Intent(this@MainActivity, sad::class.java)
        startActivity(s)
    }

    fun cpp() {
        val c = Intent(this@MainActivity, cpp::class.java)
        startActivity(c)
    }

    fun oracle() {
        val o = Intent(this@MainActivity, oracle::class.java)
        startActivity(o)
    }

    fun wordpress() {
        val w = Intent(this@MainActivity, wordpress::class.java)
        startActivity(w)
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this, R.style.alertDialagTheme)
        builder.setTitle("Exit App")
        builder.setIcon(R.drawable.icon_warning)
        builder.setMessage("Are you sure, you want to exit..?")

        builder.setPositiveButton("Yes") { dialog, which ->
            finish()
        }
        builder.setNegativeButton("No") { dialog, which ->
            Toast.makeText(this, "Clicked on no button...!", Toast.LENGTH_LONG).show()
        }
        builder.setNeutralButton("Cancel") { dialog, which ->
            Toast.makeText(this, "Clicked on cancel button...!", Toast.LENGTH_LONG).show()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
/*
    private fun checkNetworkConnection() {
        networkConnectionClass = networkConnection(application)

        networkConnectionClass.observe(this, { isConnected ->
            if (isConnected) {
                networkImg.visibility = view.GONE
            } else {
                networkImg.visibility = View.VISIBLE
            }
        }
        )
    }*/
}
