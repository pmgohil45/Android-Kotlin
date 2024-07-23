package com.example.dbfirebasepdf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.google.firebase.database.DatabaseReference

class MainActivity : AppCompatActivity() {
    var listView: ListView? = null
    var database: DatabaseReference? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }
}