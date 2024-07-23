package com.pmgohil.sessionlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var unm: EditText
    lateinit var email: EditText
    lateinit var login: Button
    lateinit var session: UserLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        unm.findViewById<EditText>(R.id.unm)
        email.findViewById<EditText>(R.id.email)
        login.findViewById<Button>(R.id.login)
        session = UserLogin(this)
        if (session.isLoggedIn()) {
            var i = Intent(applicationContext, MainActivity2::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
            finish()
        }
        login.setOnClickListener() {
            var u = unm.text.toString().trim()
            var e = email.text.toString().trim()
            if (u.isEmpty() && e.isEmpty()) {
                session.createLoginSession(u, e)
                var i = Intent(applicationContext, MainActivity::class.java)
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(i)
                finish()
            } else {
                Toast.makeText(this, "failed", Toast.LENGTH_LONG).show()
            }
        }
    }
}
