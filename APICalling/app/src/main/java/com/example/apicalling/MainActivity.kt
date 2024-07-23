package com.example.apicalling

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import com.android.volley.NetworkResponse
import org.json.JSONTokener


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val textView = findViewById<TextView>(R.id.textView
        getUsers()
    }


    // function for network call
    fun getUsers() {
        // Instantiate the RequestQueue.


        var mStatusCode = 0
        val queue = Volley.newRequestQueue(this)
        //val url = "https://jsonplaceholder.typicode.com/todos/1"
        val url = "https://newcollegeclub.000webhostapp.com/prakash/form1/apiCreate.php?id=33"
        val request: StringRequest = object : StringRequest(url,
            Response.Listener { response ->
                //Response
                val jsonObject = JSONTokener(response).nextValue() as JSONObject

                val textView1 = findViewById<TextView>(R.id.textView1)
                val textView2 = findViewById<TextView>(R.id.textView2)
                val textView3 = findViewById<TextView>(R.id.textView3)
                val textView4 = findViewById<TextView>(R.id.textView4)

                val button = findViewById<Button>(R.id.button)
                button.setOnClickListener() {

                    //user_id
                    val userId = jsonObject.getString("userId")
                    Log.e("User ID : ", userId)
                    textView1!!.text = "User ID : $userId"

                    //id
                    val id = jsonObject.getString("id")
                    Log.e("ID : ", id)
                    textView2!!.text = "ID : $id "

                    //title
                    val title = jsonObject.getString("title")
                    Log.e("Title : ", "Delectus aut autem")
                    textView3!!.text = "Title : $title"

                    //completed
                    val completed = jsonObject.getString("completed")
                    Log.e("Completed", "false")
                    textView4!!.text = "Completed : $completed"
                    //Log.e("prakash",mStatusCode.toString())
                }
            },
            Response.ErrorListener { error ->
                //Erroe

            }
        ) {
            override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                if (response != null) {
                    mStatusCode = response.statusCode
                }
                return super.parseNetworkResponse(response)
            }
        }

        queue.add(request)


    }
}