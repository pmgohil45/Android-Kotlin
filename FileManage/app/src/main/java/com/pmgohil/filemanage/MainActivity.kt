package com.pmgohil.filemanage

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView
import java.lang.Math.abs

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener {

    lateinit var gestureDetector: GestureDetector
    var x1: Float = 0.0f
    var x2: Float = 0.0f
    var y1: Float = 0.0f
    var y2: Float = 0.0f

    companion object {
        const val min_distance = 150
    }

    lateinit var txtCategories: TextView
    lateinit var txtRecents: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gestureDetector = GestureDetector(this, this)

        txtCategories = findViewById<TextView>(R.id.txtCategories)
        //txtCategories.setTextColor(Color.parseColor("#FFFFFF"));

        txtRecents = findViewById<TextView>(R.id.txtRecents)
        //txtRecents.setTextColor(Color.parseColor("#FFFFFF"));
        categoriesFrag()

        var txtRecent = findViewById<TextView>(R.id.txtRecents)
        txtRecent.setOnClickListener() {
            //txtRecents.setTextColor(Color.parseColor("#03DAC5"));
            recentFrag()
        }

        var txtCategories = findViewById<TextView>(R.id.txtCategories)
        txtCategories.setOnClickListener() {
            //txtCategories.setTextColor(Color.parseColor("#03DAC5"));
            categoriesFrag()
        }

/*        val testCategorieFragment: categorieFragment? =
            supportFragmentManager.findFragmentByTag("testID") as categorieFragment?
        val testRecentFragment: categorieFragment? =
            supportFragmentManager.findFragmentByTag("testID") as categorieFragment?
        if (testCategorieFragment != null && testCategorieFragment.isVisible()) {
            txtCategories.setTextColor(Color.parseColor("#03DAC5"));
        } else if (testRecentFragment != null && testRecentFragment.isVisible()) {
            txtRecents.setTextColor(Color.parseColor("#03DAC5"));
        }*/
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {

        gestureDetector.onTouchEvent(event)
        when (event?.action) {
            //start to swipe
            0 -> {
                x1 = event.x
                y1 = event.y
            }
            //end to swipe
            1 -> {
                x2 = event.x
                y2 = event.y

                val valueX: Float = x2 - x1
                val valueY: Float = y2 - y1

                if (abs(valueX) > min_distance) {
                    //Ditect left to right swipe
                    if (x1 > x2) {
                        categoriesFrag()
                        //txtCategories.setTextColor(Color.parseColor("#FFFFFF"));
                        //Toast.makeText(this, "Left Swipe", Toast.LENGTH_SHORT).show()
                    } else {
                        recentFrag()
                        //txtRecents.setTextColor(Color.parseColor("#FFFFFF"));
                        //Toast.makeText(this, "Right Swipe", Toast.LENGTH_SHORT).show()
                    }
                } /*else if (abs(valueY) > min_distance) {
                    //Ditect top to bottom swipe
                    if (y2 > y1) {
                        Toast.makeText(this, "Bottom Swipe", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Top Swipe", Toast.LENGTH_SHORT).show()
                    }
                }*/
            }
        }
        return super.onTouchEvent(event)
    }

    fun recentFrag() {
        supportFragmentManager.beginTransaction()
            .add(R.id.appLayout, recentFragment(), "recent fragment").commitAllowingStateLoss()
    }

    fun categoriesFrag() {
        supportFragmentManager.beginTransaction()
            .add(R.id.appLayout, categorieFragment(), "categorie fragment")
            .commitAllowingStateLoss()
    }

    fun forTheColor() {
        txtCategories.setTextColor(Color.parseColor("#03DAC5"));
        txtRecents.setTextColor(Color.parseColor("#03DAC5"));
    }


    // don't need to them

    override fun onDown(p0: MotionEvent?): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onShowPress(p0: MotionEvent?) {
        //TODO("Not yet implemented")
    }

    override fun onSingleTapUp(p0: MotionEvent?): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        //TODO("Not yet implemented")
        return false
    }

    override fun onLongPress(p0: MotionEvent?) {
        // TODO("Not yet implemented")
    }

    override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
        //TODO("Not yet implemented")
        return false
    }
}