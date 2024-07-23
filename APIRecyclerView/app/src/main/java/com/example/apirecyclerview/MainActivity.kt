package com.example.apirecyclerview

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private var data = ArrayList<ViewModel>()
    private lateinit var adapter1: customAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerview = findViewById<RecyclerView>(R.id.recyclerView)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // This will pass the ArrayList to our Adapter
        adapter1 = customAdapter(this@MainActivity, data)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter1

        getUsers()

    }

    fun getUsers() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url: String = "https://reqres.in/api/users?page=2"
        // Request a string response from the provided URL.
        try {
            val stringReq = StringRequest(Request.Method.GET, url,
                { response ->

                    var strResp = response.toString()
                    val jsonObj = JSONObject(strResp)

                    val jsonArray: JSONArray = jsonObj.getJSONArray("data")
                    Log.e("res", response.toString())

                    // var str_user: String = ""
                    for (i in 0 until jsonArray.length()) {

                        var jsonInner: JSONObject = jsonArray.getJSONObject(i)
                        Log.e("json", jsonInner.toString())

                        val id = jsonInner.getInt("id")
                        val email = jsonInner.getString("email")
                        val fnm = jsonInner.getString("first_name")
                        val lnm = jsonInner.getString("last_name")
                        val avatar = jsonInner.getString("avatar")
                        Log.e("prakash", jsonInner.toString())

                        show(id, email, fnm, lnm, avatar)
                    }

                    adapter1.notifyDataSetChanged()


                },
                Response.ErrorListener { error ->
                    //   textView!!.text = "That didn't work!"
                })

            queue.add(stringReq)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    fun show(id: Int, email: String, first_name: String, last_name: String, avatar: String) {
        val view = ViewModel()
        view.id = id
        view.email = email
        view.fnm = first_name
        view.lnm = last_name
        view.avatar = avatar
        data.add(view)
    }

}