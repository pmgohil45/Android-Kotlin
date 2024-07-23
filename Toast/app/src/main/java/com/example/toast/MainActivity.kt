package com.example.toast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var toast: Toast? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn1 = findViewById<Button>(R.id.btn1)
        btn1.setOnClickListener() {
            cancel()
            toast = Toast.makeText(this@MainActivity, "10", Toast.LENGTH_SHORT)
            toast!!.show()
        }

        val btn2 = findViewById<Button>(R.id.btn2)
        btn2.setOnClickListener() {
            cancel()
            toast = Toast.makeText(this@MainActivity, "100", Toast.LENGTH_SHORT)
            toast!!.show()
        }

        val btn3 = findViewById<Button>(R.id.btn3)
        btn3.setOnClickListener() {
            cancel()
            toast = Toast.makeText(this@MainActivity, "1000", Toast.LENGTH_SHORT)
            toast!!.show()
        }

        findViewById<Button>(R.id.btn4).setOnClickListener() {
            cancel()
            toast = Toast.makeText(this@MainActivity, "Prakash", Toast.LENGTH_SHORT)
            toast?.show()
        }
    }

    private fun cancel() {
        if (toast != null) {
            toast?.cancel()
        }
    }
}