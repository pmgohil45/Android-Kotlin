package com.example.webview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "Digital Gujarat"

        // button 1
        val homepage = findViewById<Button>(R.id.homepage)
        homepage.setOnClickListener() {
            openHomepage()
        }

        // button 2
        val aboutGujarat = findViewById<Button>(R.id.aboutGujarat)
        aboutGujarat.setOnClickListener() {
            openAboutGujarat()
        }

        //button 3
        val government = findViewById<Button>(R.id.government)
        government.setOnClickListener() {
            openGovernment()
        }
        //button 4
        val btn4 = findViewById<Button>(R.id.btn4)
        btn4.setOnClickListener() {
            openGoogle()
        }
        //button 5
        val btn5 = findViewById<Button>(R.id.btn5)
        btn5.setOnClickListener() {
            openGmail()
        }
        //button 6
        val btn6 = findViewById<Button>(R.id.btn6)
        btn6.setOnClickListener() {
            openMaps()
        }
        //button 7
        val btn7 = findViewById<Button>(R.id.btn7)
        btn7.setOnClickListener() {
            openPlayStore()
        }
    }

    // button 1
    fun openHomepage() {
        var homepage = Intent(this@MainActivity, homepage::class.java)
        startActivity(homepage)
    }

    // button 2
    fun openAboutGujarat() {
        val aboutGujarat = Intent(this@MainActivity, aboutGujarat::class.java)
        startActivity(aboutGujarat)
    }

    //button 3
    fun openGovernment() {
        val government = Intent(this@MainActivity, government::class.java)
        startActivity(government)
    }

    //button 4
    fun openGoogle() {
        val google = Intent(this@MainActivity, google::class.java)
        startActivity(google)
    }

    //button 5
    fun openGmail() {
        val gmail = Intent(this@MainActivity, gmail::class.java)
        startActivity(gmail)
    }

    //button 6
    fun openMaps() {
        val maps = Intent(this@MainActivity, mapp::class.java)
        startActivity(maps)
    }

    //button 7
    fun openPlayStore() {
        val playstore = Intent(this@MainActivity, playstore::class.java)
        startActivity(playstore)
    }
}