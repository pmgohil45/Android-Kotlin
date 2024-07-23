package com.pmgohil.thoughts

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    lateinit var txtArtOf: TextView
    lateinit var txtThoughts: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash_screen)

        txtArtOf = findViewById(R.id.txtArtOf)
        txtArtOf.startAnimation(AnimationUtils.loadAnimation(this, R.anim.trans_left))

        txtThoughts = findViewById(R.id.txtThoughts)
        txtThoughts.startAnimation(AnimationUtils.loadAnimation(this, R.anim.trans_right))

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }
}