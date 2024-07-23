package com.pmgohil.cv1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SecondActivity : AppCompatActivity() {

    lateinit var tvId: TextView
    lateinit var tvName: TextView
    lateinit var tvRoll: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        tvId = findViewById(R.id.tvId)
        tvName = findViewById(R.id.tvName)
        tvRoll = findViewById(R.id.tvRoll)

        val bundle = intent.extras
        if (bundle != null) {
            tvId.text = "id = ${bundle.getString("id")}"
            tvName.text = "Name = ${bundle.getString("name")}"
            tvRoll.text = "City = ${bundle.getString("city")}"
        }

    }
}