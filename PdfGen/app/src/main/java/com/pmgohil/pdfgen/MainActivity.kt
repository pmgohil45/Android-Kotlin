package com.pmgohil.pdfgen

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.itextpdf.text.*
import com.itextpdf.text.pdf.PdfWriter
import java.io.FileOutputStream
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var btn_pdf: Button
    private lateinit var txt_data: EditText
    private val STORAGE_CODE = 1001
    private lateinit var mFilePath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_pdf = findViewById(R.id.btn_pdf)
        txt_data = findViewById(R.id.txt_data)

        btn_pdf.setOnClickListener {

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    val permission = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permission, STORAGE_CODE)
                } else {
                    savePDF()
                }
            } else {
                savePDF()
            }

        }

    }

    private fun savePDF() {

        val mDoc = Document()
        /*val mFileName = java.text.SimpleDateFormat("yyyMMdd_HHmmss", Locale.getDefault())
            .format(System.currentTimeMillis())*/
        val mFileName = "prakash"
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            mFilePath =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                    .toString() + "/" + mFileName + ".pdf"
        } else {
            mFilePath =
                Environment.getExternalStorageDirectory().toString() + "/" + mFileName + ".pdf"
        }
        try {

            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
            mDoc.open()

            val data = txt_data.text.toString().trim()
            mDoc.addAuthor("ATOMIK")
            mDoc.add(Paragraph(data))
            mDoc.close()
            Toast.makeText(this, "$mFilePath", Toast.LENGTH_LONG).show()

        } catch (e: Exception) {
            Log.d("SV", e.message.toString())
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }
}