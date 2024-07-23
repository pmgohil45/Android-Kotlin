package com.pmgohil.fm1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout

class MainActivity : AppCompatActivity() {

    lateinit var constraintLayoutRecentStorage: ConstraintLayout
    lateinit var constraintLayoutStorage: ConstraintLayout
    lateinit var constraintLayoutDeleted: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        phoneStorage()

    }

    fun recentlyStorage() {}
    fun phoneStorage() {
        constraintLayoutStorage = findViewById(R.id.constraintLayoutStorage)

    }

    fun recentlyDeleted() {}
}