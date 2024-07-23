package com.example.project6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import java.nio.file.Files.size

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val edit = findViewById<EditText>(R.id.edit)

        val btn = findViewById<Button>(R.id.btn)
        btn.setOnClickListener() {
            //a()
        }
        val btn2 = findViewById<Button>(R.id.btn2)
        btn2.setOnClickListener() {
            //size()
            array_print()
        }
/**
        val num = arrayOf("a","b","c","d","e","f")
//        Log.e("arrayOf", num[0])
//        Log.e("arrayOf", num[1])
//        Log.e("arrayOf", num[2])
//        Log.e("arrayOf", num[3])
//        Log.e("arrayOf", num[4])
//        Log.e("arrayOf", num[5])

        for(i in 0 until num.size){
         //   Log.e("Num : " , i.toString())
            Log.e("arrayOf", num[i])
        }
  */  }

    fun array_print(){
        val a = arrayOf("Gujarat", "Gova", "Delhi", "panjaab","Rajeshthan","Jammu","Keral")
        for(i in 0 until a.size){
            Log.e("arrayOf", a[i])
        }
    }
/*

    fun a() {
        //for array size
        var n1 = findViewById<EditText>(R.id.edit)
        var txt = n1.text.toString()
        var n = 6
        for (i in 0 until txt.toInt()) {
            Handler(Looper.getMainLooper()).postDelayed({
                Log.e("for", i.toString())
            }, 100)
        }
    }

    fun size() {
        val arr = arrayOf("a", "b", "c", "d", "e", "f")
        val length = arr.size
        Log.e("size", "Before loop $length")
        for (i in 0 until length) {
            Log.e("size", arr[0].toString())
            Log.e("size", length.toString())
            Log.e("size", "---------------------------------")
        }
    }*/
}