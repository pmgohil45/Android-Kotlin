package com.example.viewpager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pageAdapter = pageAdapter(this@MainActivity)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = pageAdapter

//        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
//        tabLayout.setupWithViewPager(tabLayout)

    }
}