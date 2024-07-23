package com.example.p2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class prakash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prakash)

        //data get......... that is postponed
        val message = intent.getStringExtra("message_key")
        val text_view = findViewById<TextView>(R.id.text_view)
        text_view.text = message

        //data put
        var msg_edit_text1 = findViewById<EditText>(R.id.editText1)
        var button1 = findViewById<Button>(R.id.button2)

        button1.setOnClickListener() {

            val message2 = Intent()
            message2.putExtra("MESSAGE", message)
            setResult(2, message2)
            finish() //finishing activity
/*
            // finish code
            Intent intent = new Intent()
            intent.putExtra("MESSAGE", message)
            setResult(2, intent)
            finish()//finishing activity
  */
        }
    }
}