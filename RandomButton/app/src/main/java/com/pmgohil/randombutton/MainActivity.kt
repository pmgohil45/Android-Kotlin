package com.pmgohil.randombutton

import android.R.attr.button
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Button
import androidx.activity.ComponentActivity
import java.util.Random
import java.util.Timer
import java.util.TimerTask


class MainActivity : ComponentActivity() {
    lateinit var btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = btn.findViewById(R.id.btn)
        btn.setOnClickListener() {

            val displaymetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displaymetrics)
            val timer = Timer()

            timer.schedule(object : TimerTask() {
                override fun run() {
                    runOnUiThread {
                        val R = Random()
                        val dx: Float = R.nextFloat() * displaymetrics.widthPixels
                        val dy: Float = R.nextFloat() * displaymetrics.heightPixels
                        val timer = Timer()
                        btn.animate()
                            .x(dx)
                            .y(dy)
                            .setDuration(0)
                            .start()
                    }
                }
            }, 0, 1000)
        }

    }
}