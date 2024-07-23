package com.example.json

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var textViewName: TextView
    lateinit var textViewSal: TextView
    lateinit var textView3: TextView
    var jsonString =
        "{\"employee\":{\"name\":\"Prakash Gohil\",\"salary\":9000$,\"designetion\":developer}}"
    lateinit var name: String
    lateinit var salary: String
    lateinit var add: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Pm Gohil"
        textViewName = findViewById(R.id.textViewName)
        textViewSal = findViewById(R.id.textViewSal)
        textView3 = findViewById(R.id.textView3)
        try {
            // get JSONObject from JSON file
            val obj = JSONObject(jsonString)
            // fetch JSONObject named employee
            val employee: JSONObject = obj.getJSONObject("employee")
            // get employee name and salary
            name = employee.getString("name")
            salary = employee.getString("salary")
            add = employee.getString("designetion")
            // set employee name and salary in TextView's
            textViewName.text = "Name: $name"
            textViewSal.text = "Salary: $salary"
            textView3.text = "Designetion: $add"
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}