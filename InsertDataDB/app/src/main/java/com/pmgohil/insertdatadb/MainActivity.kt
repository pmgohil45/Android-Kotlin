package com.pmgohil.insertdatadb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    lateinit var btnClear: Button
    lateinit var btnSub: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      /*  btnClear.findViewById<Button>(R.id.btnClear)
        btnClear.setOnClickListener() {}*/

    }
}