package com.example.design

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import layout.adapter

class MainActivity : AppCompatActivity() {
    private lateinit var adapter1: adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val arrData = ArrayList<String>()
        arrData.add("Test")
        arrData.add("Demo")
        arrData.add("Hello")

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter1 = adapter(this@MainActivity,arrData)
        recyclerView.adapter = adapter1
    }
}