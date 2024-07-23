package com.example.project7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn0 = findViewById<Button>(R.id.btn0)
        btn0.setOnClickListener() {
            val edit_text = findViewById<EditText>(R.id.edit_text)
            val a = edit_text.text.toString()
            val length = a.length

            Log.e("for", a.toString())
//            for (i in 0 until a.toInt() ) {
//                Log.e("for", i.toString())
//            }
//        }
            val btn1 = findViewById<Button>(R.id.btn1)
            btn1.setOnClickListener() {
                val police1 = arrayOf("ACP", "DCP", "PSI", "PSO", "PI", "IS")
                Log.e("array", police1[1].toString())
            }

            val btn2 = findViewById<Button>(R.id.btn2)
            btn2.setOnClickListener() {
                val police2 = arrayOf("ACP", "DCP", "PSI", "PSO", "PI", "IS")
                val langth2 = police2.size
                for (i in 0 until langth2)// i --> it means name of data of colum name
                {
                    Log.e("array", police2[i].toString())
                }
            }

            val btn3 = findViewById<Button>(R.id.btn3)
            btn3.setOnClickListener() {
                val police3 = arrayOf("ACP", "DCP", "PSI", "PSO", "PI", "IS")
                val langth3 = police3.size
                for (i in 1 until langth3) {
                    Log.e("array", langth3.toString())
                    //its write for the more time print langth if i don't write this then print only 2 times.
                    Log.e("array", "-----------------")
                }
            }

            val btn4 = findViewById<Button>(R.id.btn4)
            btn4.setOnClickListener() {
                val police4 = arrayOf("ACP", "CEP", "PSI", "PSO", "PI", "IS")
                val langth4 = police4.size
                for (i in 1 until langth4) {
                    Log.e("array", i.toString())
                }
            }
        }
    }
}
