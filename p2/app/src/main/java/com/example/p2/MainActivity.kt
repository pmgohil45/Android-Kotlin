package com.example.p2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        var msg_edit_text = findViewById<EditText>(R.id.editText)
        var button = findViewById<Button>(R.id.button)
        button.setOnClickListener() {
            val intent = Intent(this@MainActivity, prakash::class.java)
            val message1 = msg_edit_text.text.toString()
            intent.putExtra("message_key", message1)
            startActivity(intent)
        }

    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            // your operation...
        }
    }
}