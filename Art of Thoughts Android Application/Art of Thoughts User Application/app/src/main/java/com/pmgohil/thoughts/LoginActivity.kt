package com.pmgohil.thoughts

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    lateinit var ll_select_image_text: LinearLayout
    lateinit var image_view_select_image: ImageView
    lateinit var txt_image_status: TextView
    lateinit var et_name: EditText
    lateinit var et_email: EditText
    lateinit var btn_yes_submit: Button
    private val REQUEST_GALLERY = 2
    lateinit var btnSignInWithOTP: Button
    lateinit var btnSignInWithGoogle: Button
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val req_Code: Int = 123
    private lateinit var databaseReference: DatabaseReference
    private lateinit var get_name_from_mail_id: String
    private lateinit var get_email_from_mail_id: String
    private lateinit var get_image_url_from_mail_id: String
    private lateinit var remove_domain_name: String
    private lateinit var internal_image: String
    private lateinit var image_path: String
    private lateinit var sharedPreferencesManager: SharedPreferencesManager


    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UseCompatLoadingForDrawables", "MissingInflatedId", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.color.transparent))
        try {
            ll_select_image_text = findViewById(R.id.ll_select_image_text)
            image_view_select_image = findViewById(R.id.image_view_select_image)
            txt_image_status = findViewById(R.id.txt_image_status)
            et_name = findViewById(R.id.et_name)
            et_email = findViewById(R.id.et_email)
            btn_yes_submit = findViewById(R.id.btn_yes_submit)
            btnSignInWithOTP = findViewById(R.id.btnSignInWithOTP)
            btnSignInWithGoogle = findViewById(R.id.btnSignInWithGoogle)

            // Initialize SharedPreferencesManager
            sharedPreferencesManager = SharedPreferencesManager(this)

            // Initialize Firebase Database
            firebaseAuth = FirebaseAuth.getInstance()
            databaseReference = FirebaseDatabase.getInstance().reference


            ll_select_image_text.setOnClickListener() {
                show_options_dialog_for_camera_gallery_files()
            }


            btn_yes_submit.setOnClickListener() {
                manual_insert_data_in_firebase_database()
            }

            btnSignInWithGoogle.setOnClickListener() { google_sign_in() }
        } catch (e: Exception) {
            GlobalVariableFunction.showToast(this, e.toString())
        }
    }

    /************************** Manual Login **************************/

    private fun show_options_dialog_for_camera_gallery_files() {
        open_gallery()
    }

    private fun open_gallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    private fun getImagePath(uri: Uri): String {
        return uri.toString()
    }

    private fun manual_insert_data_in_firebase_database() {
        try {
            if (internal_image.isNotEmpty() && et_email.text.toString()
                    .isNotEmpty() && et_name.text.toString().isNotEmpty()
            ) {
                remove_domain_name = remove_domain_name_from_mail(et_email.text.toString())
                GlobalVariableFunction.email = et_email.text.toString()
                GlobalVariableFunction.user_name = remove_domain_name
                GlobalVariableFunction.internal_image = internal_image
                GlobalVariableFunction.name = et_name.text.toString()
                get_image_url_from_mail_id = "null"

                val userData = UserData(
                    et_name.text.toString(),
                    et_email.text.toString(),
                    remove_domain_name,
                    get_image_url_from_mail_id,
                    internal_image
                )
                databaseReference.child("users").child(remove_domain_name).setValue(userData)
                    .addOnSuccessListener {
                        // Save data to SharedPreferences
                        sharedPreferencesManager.imageUrl = internal_image
                        sharedPreferencesManager.name = et_name.text.toString()
                        sharedPreferencesManager.username = remove_domain_name
                        sharedPreferencesManager.email = et_email.text.toString()
                        sharedPreferencesManager.save()

                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    }.addOnFailureListener {
                        GlobalVariableFunction.showToast(this, "Data not insert")
                    }
            } else {
                GlobalVariableFunction.showToast(this, "Please, Fill the above fields!")
            }
        } catch (e: Exception) {
            GlobalVariableFunction.showToast(this, "Please, Fill the above fields!")
        }
    }

    /************************** Sign In with Google **************************/
    fun google_sign_in() {

        FirebaseApp.initializeApp(this)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        signInGoogle()

    }

    private fun signInGoogle() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, req_Code)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            req_Code -> {
                // Handle Google Sign-In result
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        val task: Task<GoogleSignInAccount> =
                            GoogleSignIn.getSignedInAccountFromIntent(data)
                        handleResult(task)
                    } catch (e: ApiException) {
                        GlobalVariableFunction.showToast(this, "Google Sign In Failed - $e")
                    }
                }
            }

            REQUEST_GALLERY -> {
                if (resultCode == Activity.RESULT_OK) {
                    val selectedImageUri: Uri? = data?.data
                    selectedImageUri?.let { uri ->
                        internal_image = getImagePath(uri)
                        Glide.with(image_view_select_image).load(internal_image)
                            .placeholder(R.drawable.right).error(R.drawable.wrong)
                            .into(image_view_select_image)
                        txt_image_status.text = "Image Selected."
                        txt_image_status.setTextColor(getColor(R.color.green))
                    }
                }
            }


            else -> {
                // Handle other request codes if needed
            }
        }
    }


    private fun handleResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException) {
            //GlobalVariableFunction.showToast(this, "HR: $e")
            Log.w(TAG, "signInWithCredential:failure: $e")
        }
    }

    // this is where we update the UI after Google signin takes place
    private fun UpdateUI(account: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                val displayName = account.displayName
                val email = account.email
                val photoUrl = account.photoUrl

                get_name_from_mail_id = displayName.toString()
                get_email_from_mail_id = email.toString()
                get_image_url_from_mail_id = photoUrl.toString()
                internal_image = photoUrl.toString()
                remove_domain_name = remove_domain_name_from_mail(get_email_from_mail_id)
                val user_data = UserData(
                    get_name_from_mail_id,
                    get_email_from_mail_id,
                    remove_domain_name,
                    get_image_url_from_mail_id,
                    internal_image
                )

                //store data in global variable
                GlobalVariableFunction.email = get_email_from_mail_id
                GlobalVariableFunction.user_name = remove_domain_name
                GlobalVariableFunction.name = get_name_from_mail_id

                databaseReference.child("users").child(GlobalVariableFunction.user_name).get()
                    .addOnSuccessListener {
                        // Save Data in SharedPreferencesManager
                        sharedPreferencesManager.name = displayName.toString()
                        sharedPreferencesManager.email = email.toString()
                        sharedPreferencesManager.username = remove_domain_name
                        sharedPreferencesManager.imageUrl = photoUrl.toString()
                        sharedPreferencesManager.save()
                        if (it.exists()) {
                            val name = it.child("name").value.toString()
                            val internal_image = it.child("internal_image").value.toString()
                            if (internal_image == "null") {
                                sharedPreferencesManager.name = name
                                sharedPreferencesManager.imageUrl = photoUrl.toString()
                                sharedPreferencesManager.save()
                            } else {
                                sharedPreferencesManager.name = name
                                sharedPreferencesManager.imageUrl = internal_image
                                sharedPreferencesManager.save()
                            }
                        }
                    }.addOnFailureListener {
                        GlobalVariableFunction.showToast(this, "Failed")
                    }

                databaseReference.child("users").child(GlobalVariableFunction.user_name).get()
                    .addOnSuccessListener {
                        if (it.exists()) {
                            val unm = it.child("unm").value.toString()
                            if (unm == "null") {
                                insert_data_in_firebase_database(user_data)
                            }
                        } else {
                            insert_data_in_firebase_database(user_data)
                        }
                    }.addOnFailureListener {
                        GlobalVariableFunction.showToast(this, "Failed")
                    }

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this, "Sign in Successful...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (GoogleSignIn.getLastSignedInAccount(this) != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    @SuppressLint("InflateParams")


    private fun insert_data_in_firebase_database(userData: UserData) {
        databaseReference.child("users").child(remove_domain_name).setValue(userData)
            .addOnSuccessListener {
                //showToast("Data insert")
            }.addOnFailureListener {
                GlobalVariableFunction.showToast(this, "Data not insert")
            }

    }

    private fun show_app_leave_dialog_box() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit").setMessage("Are you sure you want to leave?")
        builder.setPositiveButton("Yes") { dialog, which ->
            finish()
        }
        builder.setNegativeButton("No") { dialog, which ->
            GlobalVariableFunction.showToast(this, "Yes, I want to stay.")
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        /*   super.onBackPressed()
        show_app_leave_dialog_box()*/
    }

    fun remove_domain_name_from_mail(email: String): String {
        val regex = Regex("(.+)@(.+)")
        val matchResult = regex.find(email)

        return matchResult?.let {
            // Group 1 contains the part before "@"
            it.groupValues[1]
        } ?: email
    }

}
