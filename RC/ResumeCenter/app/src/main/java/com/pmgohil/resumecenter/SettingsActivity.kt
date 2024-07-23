package com.pmgohil.resumecenter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout

class SettingsActivity : AppCompatActivity() {
    lateinit var developerInfoRL: RelativeLayout
    lateinit var privacyPolicyRL: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        /*developerInfoRL = findViewById(R.id.developerInfoRL)
        developerInfoRL.setOnClickListener() {
            val intent = Intent(this, DeveloperActivity::class.java)
            startActivity(intent)
        }*/
        privacyPolicyRL = findViewById(R.id.privacyPolicyRL)
        privacyPolicyRL.setOnClickListener() {
            val intent = Intent(this, PrivacyPolicyActivity::class.java)
            startActivity(intent)
        }
    }
}