package com.pmgohil.marksheet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val name = "John"
        val s1 = 10
        val s2 = 10
        val s3 = 10
        val mark = s1 + s2 + s3
        println("my name is $name\nTotaal Mark is : $mark")


    }
}