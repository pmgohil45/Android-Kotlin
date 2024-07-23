package com.example.dbfirebase

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.dbfirebase.databinding.ActivityMainBinding

import com.google.android.gms.common.internal.Objects
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.File


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.getImage.setOnClickListener {

            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Fetching image....!")
            progressDialog.setCancelable(false)
            progressDialog.show()

            val imageName = binding.etImageId.text.toString()
            val storageRef = FirebaseStorage.getInstance().reference.child("images/$imageName.jpg")
            val localfile = File.createTempFile("tempImage", "JPG")

            storageRef.getFile(localfile).addOnSuccessListener {

                if (progressDialog.isShowing) {
                    progressDialog.dismiss()
                }

                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                binding.imageView.setImageBitmap(bitmap)
            }.addOnFailureListener {
                if (progressDialog.isShowing) {
                    progressDialog.dismiss()
                }
                Toast.makeText(this, "failed to retrieve the image", Toast.LENGTH_SHORT).show()
            }
        }
    }
}