package com.pmgohil.filemanage

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.jar.Manifest

class allPhotoActivity : AppCompatActivity() {

    lateinit var rs: Cursor
    lateinit var gridView: GridView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_photo)
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                Array(1) { android.Manifest.permission.READ_EXTERNAL_STORAGE }, 121
            )
        }
        listImages()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 121 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            listImages()
        }
    }

    private fun listImages() {
        var cols = listOf<String>(MediaStore.Images.Thumbnails.DATA).toTypedArray()
        rs =
            contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                cols,
                null,
                null,
                null
            )!!
        gridView = findViewById<GridView>(R.id.gridView)
        gridView.adapter = ImageAdapter(applicationContext)
        gridView.setOnItemClickListener { adapterView, view, i, l ->
            rs.moveToPosition(i)
            var path = rs.getString(0)
            var intent = Intent(applicationContext, showImageActivity::class.java)
            intent.putExtra("path", path)
            startActivity(intent)
        }
    }

    inner class ImageAdapter : BaseAdapter {
        lateinit var context: Context

        constructor(context: Context) {
            this.context = context
        }

        override fun getCount(): Int {
            return rs.count
        }

        override fun getItem(p0: Int): Any {
            return p0
        }

        override fun getItemId(p0: Int): Long {
            return p0.toLong()
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            var iv = ImageView(context)
            rs.moveToPosition(p0)// fist colum of grid view
            var path = rs.getString(0)//image name
            var bitmap = BitmapFactory.decodeFile(path)
            iv.setImageBitmap(bitmap)
            iv.layoutParams = AbsListView.LayoutParams(400, 400)
            return iv
        }

    }

}
