package com.pmgohil.cv1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import java.sql.DriverManager

class MainActivity : AppCompatActivity() {

    lateinit var id: EditText
    lateinit var nm: EditText
    lateinit var city: EditText
    lateinit var btnSub: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        id = findViewById(R.id.id)
        nm = findViewById(R.id.nm)
        city = findViewById(R.id.city)
        btnSub = findViewById(R.id.btnSub)

        btnSub.setOnClickListener {

            /*val bundle = Bundle()
            bundle.putString("id", id.text.toString())
            bundle.putString("name", nm.text.toString())
            bundle.putString("city", city.text.toString())

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)*/
        }
    }
}