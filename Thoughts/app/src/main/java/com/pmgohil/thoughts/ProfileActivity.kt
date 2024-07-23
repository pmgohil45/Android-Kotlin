package com.pmgohil.thoughts

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ProfileActivity : AppCompatActivity() {
    lateinit var image_user_profile: ImageView
    lateinit var txt_user_name: TextView
    lateinit var txt_user_mail_id: TextView
    lateinit var ll_profile_edittext: LinearLayout
    lateinit var image_save_text: ImageView
    lateinit var edit_txt_user_name: EditText
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private val REQUEST_CAMERA = 1
    private val REQUEST_GALLERY = 2
    private val REQUEST_FILE = 3
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    @SuppressLint("UseCompatLoadingForDrawables", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.color.transparent))

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        image_user_profile = findViewById(R.id.image_user_profile)
        txt_user_name = findViewById(R.id.txt_user_name)
        edit_txt_user_name = findViewById(R.id.edit_txt_user_name)
        txt_user_mail_id = findViewById(R.id.txt_user_mail_id)
        ll_profile_edittext = findViewById(R.id.ll_profile_edittext)
        image_save_text = findViewById(R.id.image_save_text)

        sharedPreferencesManager = SharedPreferencesManager(this)
        /************************** Back Button on Action Bar Code **************************/
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /************************** Click Listener **************************/

        txt_user_name.setOnClickListener() {
            txt_user_name.visibility = View.GONE
            ll_profile_edittext.visibility = View.VISIBLE
            edit_txt_user_name.setText(txt_user_name.text.toString())
            edit_txt_user_name.requestFocus()
        }
        image_save_text.setOnClickListener() {
            txt_user_name.visibility = View.VISIBLE
            ll_profile_edittext.visibility = View.GONE
            txt_user_name.text = edit_txt_user_name.text
            name_update_in_firebase_database(txt_user_name.text.toString())
        }
        txt_user_mail_id.setOnClickListener() {
            GlobalVariableFunction.showToast(this, "You can not change email id!")
        }
        image_user_profile.setOnClickListener() {
            show_options_dialog_for_camera_gallery_files()
        }

        /************************** Function Calls **************************/
        select_data_from_firebase_realtime_database()

    }


    private fun show_options_dialog_for_camera_gallery_files() {
        open_gallery()
    }

    private fun open_gallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_CAMERA, REQUEST_GALLERY, REQUEST_FILE -> {
                    val selectedImageUri: Uri? = data?.data
                    selectedImageUri?.let { uri ->
                        val imagePath = getImagePath(uri)
                        image_path_update_in_firebase_database(imagePath)
                    }
                }
            }
        }
    }

    private fun getImagePath(uri: Uri): String {
        return uri.toString()
    }

    private fun image_path_update_in_firebase_database(image_path: String) {
        val update_image_path = mapOf<String, Any>("internal_image" to image_path)
        databaseReference.child("users").child(GlobalVariableFunction.user_name)
            .updateChildren(update_image_path).addOnSuccessListener {
                sharedPreferencesManager.imageUrl = update_image_path.toString()
                sharedPreferencesManager.save()
                select_data_from_firebase_realtime_database()
            }.addOnFailureListener {
                GlobalVariableFunction.showToast(this, "Image Not Update")
            }
    }

    private fun name_update_in_firebase_database(name: String) {
        val update_name = mapOf<String, Any>("name" to name)
        GlobalVariableFunction.name = name
        databaseReference.child("users").child(GlobalVariableFunction.user_name)
            .updateChildren(update_name).addOnSuccessListener {
                sharedPreferencesManager.name = update_name.toString()
                sharedPreferencesManager.save()
                select_data_from_firebase_realtime_database()
            }.addOnFailureListener {
                GlobalVariableFunction.showToast(this, "Image Not Update")
            }
    }

    private fun select_data_from_firebase_realtime_database() {

        databaseReference.child("users").child(GlobalVariableFunction.user_name).get()
            .addOnSuccessListener {
                if (it.exists()) {
                    txt_user_name.text = it.child("name").value.toString()
                    //GlobalVariableFunction.name = txt_user_name.toString()
                    txt_user_mail_id.text = it.child("email").value.toString()
                    val internal_image_url = it.child("internal_image").value.toString()
                    val mail_id_image_url = it.child("mail_id_image").value.toString()
                    GlobalVariableFunction.internal_image = internal_image_url
                    GlobalVariableFunction.mail_id_image = mail_id_image_url
                    if (GlobalVariableFunction.internal_image != "null" || GlobalVariableFunction.mail_id_image != "null") {
                        if (internal_image_url == "null") {
                            mail_id_image_url?.let {
                                Glide.with(this).load(it).into(image_user_profile)
                            }
                            image_user_profile.visibility = View.VISIBLE
                        } else {
                            internal_image_url?.let {
                                Glide.with(this).load(it).into(image_user_profile)
                            }
                            image_user_profile.visibility = View.VISIBLE
                        }
                    }
                }
            }.addOnFailureListener {
                GlobalVariableFunction.showToast(this, "Failed")
            }

    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        finish()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}