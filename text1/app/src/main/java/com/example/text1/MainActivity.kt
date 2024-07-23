package com.example.text1

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {
    lateinit var t1: EditText
    lateinit var view: TextView
    val PERMISSION_REQUEST_CODE = 200
    var pageHeight = 1120
    var pagewidth = 792

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        t1 = findViewById<EditText>(R.id.t1)
        view = findViewById<TextView>(R.id.v1)
        val button = findViewById<Button>(R.id.b1)
        button.setOnClickListener() {
            val finle = t1.text
            view.text = finle
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_REQUEST_CODE)
            } else {
                t1.text = null
                createPdf()
            }


        }
    }

    @SuppressLint("SetTextI18n")
    private fun createPdf() {
        val pdfDocument = PdfDocument()
        //val paint = Paint()
        val title = Paint()
        val mypageInfo = PdfDocument.PageInfo.Builder(pagewidth, pageHeight, 1).create()
        val myPage = pdfDocument.startPage(mypageInfo)
        val canvas: Canvas = myPage.canvas
        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL))
        title.setTextSize(15F)
        title.setColor(ContextCompat.getColor(this, R.color.purple_200))
        canvas.drawText("A portal for IT professionals.", 209F, 100F, title)
        canvas.drawText("Geeks for Geeks", 209F, 80F, title)
        title.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL))
        title.setColor(ContextCompat.getColor(this, R.color.purple_200))
        title.setTextSize(15F)
        title.setTextAlign(Paint.Align.CENTER)
        canvas.drawText("This is sample document which we have created.", 396F, 560F, title)
        pdfDocument.finishPage(myPage)
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"p.pdf")
        //val file = File(Environment.getExternalStorageDirectory(), "p.pdf")
        //val file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            t1.setText("successful")
        } catch (e: IOException) {
            //e.printStackTrace()
            t1.setText("Error ~> $e")
        }
        pdfDocument.close();

    }

}