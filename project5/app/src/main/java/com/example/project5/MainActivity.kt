package com.example.project5

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recycler_view = findViewById<RecyclerView>(R.id.recycler)
        recycler_view.layoutManager = LinearLayoutManager(this)
        var num: Array<String> = arrayOf(
            "red",
            "blue",
            "cyan",
            "magento",
            "whait",
            "prakash",
            "dharmik",
            "brijesh",
            "harsh",
            "rajkot",
            "gujarat"
        )


//        val arrValue = listOf(num)
        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(num)
        recycler_view.adapter = adapter
    }

    fun Context.toast(prakash: CharSequence) {
        Toast.makeText(this, prakash, Toast.LENGTH_SHORT).show()
    }

}