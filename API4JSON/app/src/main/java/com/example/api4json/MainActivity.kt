package com.example.api4json

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView1 = findViewById<TextView>(R.id.textView1)
        val textView2 = findViewById<TextView>(R.id.textView2)
        val textView3 = findViewById<TextView>(R.id.textView3)
        val textView4 = findViewById<TextView>(R.id.textView4)
        val url = "https://reqres.in/api/users?page=2"
        val queue = Volley.newRequestQueue(this)

        try {

            // Request a string response from the provided URL.
            val stringReq = StringRequest(
                Request.Method.GET,
                url,
                Response.Listener<String> { response ->
                    var strResp = response.toString()
                    //Log.e("ab", response.toString()) //print all response
                    val jsonObject: JSONObject = JSONObject(strResp.toString())

                    val jsonArr = jsonObject.getJSONArray("data")
                    for (i in 0 until jsonArr.length()) {
                        val subObj = jsonArr.getJSONObject(i)

                        val id = subObj.getString("id")
                        Log.e("json", "id : $id")
                        textView1!!.text = "ID : $id"

                        val email = subObj.getString("email")
                        Log.e("json","email : $email")
                        textView2!!.text = "Email : $email"

                        val fnm = subObj.getString("first_name")
                        Log.e("json","First Name : $fnm")
                        textView3!!.text = "First Name : $fnm"

                        val lnm = subObj.getString("last_name")
                        Log.e("json","Last Name : $lnm")
                        textView4!!.text = "Last Name : $lnm"

                    }
                },
                Response.ErrorListener { error ->
                    //textView!!.text = "That didn't work!"
                })
            queue.add(stringReq)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}