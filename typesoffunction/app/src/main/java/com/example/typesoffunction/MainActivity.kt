package com.example.typesoffunction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var text1: TextView
    lateinit var text2: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        text1 = findViewById<TextView>(R.id.textViwe1)
        text2 = findViewById<TextView>(R.id.textViwe2)
        val text3 = findViewById<TextView>(R.id.textViwe3)
        val text4 = findViewById<TextView>(R.id.textViwe4)

        a()

        val num1 = 10
        val num2 = 20
        b(num1, num2)


        val sum = c()
        Log.e("abcd", "This is my sum $sum")
        Log.e("abcd", sum.toString())
        text3.text = sum.toString()

        val a2 = 40
        val b2 = 50
        val sum1 = d(a2, b2)
        Log.e("abcd", sum1.toString())
        text4.text = sum1.toString()
    }

    // without argument and without return value
    fun a() {
        Log.e("abcd", "function 1")
        text1.text = "function 1"

    }

    // with argument and without return value
    fun b(num1: Int, num2: Int) {
        val c = num1 + num2;
        Log.e("abcd", c.toString())
        text2.text = c.toString()
    }

    // withour argument and with return value
    fun c(): Int {
        val a1 = 30
        val b1 = 40
        val c1 = a1 + b1
        return c1
    }

    // with argumetn and with return value
    fun d(a2: Int, b2: Int): Int {
        val c2 = a2 + b2
        return c2
    }
}