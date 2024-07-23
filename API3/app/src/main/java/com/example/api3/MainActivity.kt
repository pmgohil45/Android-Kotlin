package com.example.api3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text1 = findViewById<TextView>(R.id.textView1)
        val text2 = findViewById<TextView>(R.id.textView2)

        val jsonStr = "{\"employee\":{\"name\":\"adolf hitler\",\"salary\":65000}}"

        val jsonObj = JSONObject(jsonStr)
        val obj : JSONObject = jsonObj.getJSONObject("employee")

        val nm = obj.getString("name")
        text1!!.text = "Employee : $nm"

        val sal = obj.getString("salary")
        text2!!.text = "Salary : $sal"
    }
}