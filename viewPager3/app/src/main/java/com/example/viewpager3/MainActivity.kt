package com.example.viewpager3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pageAdapter = frag1(this@MainActivity)
        val viewPager2 = findViewById<ViewPager2>(R.id.viewPager2)
        viewPager2.adapter = pageAdapter
    }
}