package com.example.listexternalstoragefiles

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi

import java.io.File
import java.util.concurrent.Flow

class MainActivity : AppCompatActivity() {

    private val requestCode = 100
    private lateinit var txtFiles: TextView
    //private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtFiles = findViewById<TextView>(R.id.txtFiles)
    }

    fun list(view: View) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), requestCode)
        } /*else {
            listExternalStorage()
        }*/
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == this.requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                //     listExternalStorage()
            } else {
                Toast.makeText(
                    this,
                    "Until you grant the permission, I cannot list the files",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        //this.disposable?.dispose()
    }

    /*private fun listExternalStorage() {
        val state = Environment.getExternalStorageState()

        if (Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state) {
            this.disposable =
                io.reactivex.bservable.fromPublisher(FileLister(Environment.getExternalStorageDirectory()))
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        val txtFile = findViewById<TextView>(R.id.txtFiles)
                        txtFile.append(it + "\n")
                        Log.e("im", "Yes ----->")
                    }, {
                        Log.e("MainActivity", "Error in listing files from the SD card", it)
                    }, {
                        Toast.makeText(
                            this,
                            "Successfully listed all the files!",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                *//*        this.disposable?.dispose()
                        this.disposable = null*//*
                    })
        }
    }*/

    @RequiresApi(Build.VERSION_CODES.R)
    private class FileLister(val directory: File) : Flow.Publisher<String> {

        private lateinit var subscriber: Flow.Subscriber<in String>

        @RequiresApi(Build.VERSION_CODES.R)
        override fun subscribe(s: Flow.Subscriber<in String>?) {
            if (s == null) {
                return
            }
            this.subscriber = s
            this.listFiles(this.directory)
            this.subscriber.onComplete()
        }

        fun getDownloadedFile() {
            val path = Environment.getExternalStorageDirectory().toString() + "/Pm Gohil/"
            Log.d("Files", "Path: $path")

            val directory = File(path)
            val files = directory.listFiles()
            if (directory.canRead() && files != null) {
                Log.d("Files", "Size: " + files.size)
                for (file in files) Log.d("FILE", file.name)
            } else Log.d("Null?", "it is null")
        }

        /**
         * Recursively list files from a given directory.
         */
        private fun listFiles(directory: File) {
            val files = directory.listFiles()
            if (files != null) {
                for (file in files) {
                    if (file != null) {
                        if (file.isDirectory) {
                            listFiles(file)
                        } else {
                            subscriber.onNext(file.absolutePath)
                        }
                    }
                }
            }
        }
    }
}