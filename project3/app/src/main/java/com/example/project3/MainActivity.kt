package com.example.project3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import java.lang.reflect.GenericArrayType
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var button = findViewById<Button>(R.id.button)
        button.setOnClickListener() {
            //a()
            //b()
            //c()
            //d()
            //e()
            //f()
            g()
        }
    }
    fun a(){
        var sal = 18
        if (sal <= 5)
        {
            Log.e("sal", "able")
        }
        else
        {
            Log.e("sal", "not able")
        }
    }
    fun b(){
        val color = 1
        when(color){
            1 -> Log.e("for","red")
            2 -> Log.e("for","blue")
            3 -> Log.e("for","cayn")
            4 -> Log.e("for","yellow")
            5 -> Log.e("for","white")
            6 -> Log.e("for","grey")
            else-> Log.e("for","no color")
        }
    }
    fun c(){
        for (a in 5 downTo 1 step 2) {
            Log.e("for", a.toString())
        }
    }
    fun d(){
        var num = 12
        while(num!=10)
        {
            Log.e("while","This is the lucky number dear...")
        }
    }
    fun e(){
        var num = 12
        do {
            Log.e("do", "Its very nice...")
        }while(num != 10)
    }
    fun f(){
        var num = 5
        while(num<=5)
        if(num <= 5){
            Log.e("if","ys, right")
            //continue
            break
        }
    }
    fun g(){
        val num = arrayOf(1,2,3,4,5)
       // num.get(0)
       // num.get(1)
        num.set(2,30)
        num.set(3,20)

        for(i in 1..5){
            Log.e("for", Array.toString (num))
    /*  Log.e("arrayOf", num.get(0).toString())
        Log.e("arrayOf", num[1].toString())
        Log.e("arrayOf", num[2].toString())
        Log.e("arrayOf", num.get(3).toString())
    */
        }
    }
}