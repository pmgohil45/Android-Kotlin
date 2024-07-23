package com.pmgohil.sessionlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity2 : AppCompatActivity() {
    lateinit var unm: TextView
    lateinit var email: TextView
    lateinit var btnLogin: Button
    lateinit var btnLogout: Button
    lateinit var session: UserLogin

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        btnLogin = findViewById(R.id.btnLogin)
        btnLogout = findViewById(R.id.btnLogout)
        unm.findViewById<TextView>(R.id.tvUnm)
        email.findViewById<TextView>(R.id.tvEmail)


        session = UserLogin(this)
        var user: HashMap<String, String> = session.getUserDetails()
        var unm1 = user.get(UserLogin.KEY_USERNAME)
        var email1 = user.get(UserLogin.KEY_EMAIL)
        unm.setText(unm1)
        email.setText(email1)

        btnLogin.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        btnLogout.setOnClickListener() {
            session.logoutUser()
        }


    }
}