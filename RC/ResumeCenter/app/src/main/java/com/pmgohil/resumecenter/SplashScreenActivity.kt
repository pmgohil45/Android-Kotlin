package com.pmgohil.resumecenter

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class SplashScreenActivity : AppCompatActivity() {
    lateinit var txtResume: TextView
    lateinit var txtCenter: TextView
    lateinit var llStar: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        txtResume = findViewById(R.id.txtResume)
        txtResume.startAnimation(AnimationUtils.loadAnimation(this, R.anim.trans_left))

        txtCenter = findViewById(R.id.txtCenter)
        txtCenter.startAnimation(AnimationUtils.loadAnimation(this, R.anim.trans_right))

        llStar = findViewById(R.id.llStar)
        llStar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.scale))

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }
}