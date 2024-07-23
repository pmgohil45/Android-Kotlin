package com.pmgohil.randomcodegeneratoremial

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var mail: EditText
    lateinit var otp: EditText
    var code: Int? = null
    lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mail = findViewById(R.id.etMail)
        otp = findViewById(R.id.etCode)
        btn = findViewById(R.id.btn)
        btn.setOnClickListener() {
            code = Random().nextInt(8999) + 1000
            Toast.makeText(this, "code is : " + code, Toast.LENGTH_LONG).show()
            val url: String = ""

        }
    }
}