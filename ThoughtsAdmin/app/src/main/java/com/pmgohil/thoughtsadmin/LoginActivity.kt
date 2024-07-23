package com.pmgohil.thoughtsadmin

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    lateinit var et_email: EditText
    lateinit var et_password: EditText
    lateinit var btnSignIn: Button
    lateinit var btn_send_request_for_admin: Button
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    lateinit var email_removed_domain: String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.color.transparent))

        et_email = findViewById(R.id.et_email)
        et_password = findViewById(R.id.et_password)
        btnSignIn = findViewById(R.id.btnSubmitRequest)
        btn_send_request_for_admin = findViewById(R.id.btn_send_request_for_admin)

        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        btnSignIn.setOnClickListener() {
            email_removed_domain = remove_domain_name_from_mail(et_email.text.toString())
            loginUser(email_removed_domain)

        }

        btn_send_request_for_admin.setOnClickListener() {
            //openEmailClient()
            startActivity(Intent(this, SendAdminRequestActivity::class.java))
        }

    }

    private fun loginUser(email_removed_domain: String) {
        // Retrieve data from users node
        databaseReference.child("admin_user").child("$email_removed_domain").get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val email = it.child("email").value.toString()
                    val password = it.child("password").value.toString()
                    if (email == et_email.text.toString() && password == et_password.text.toString()) {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                        GlobalVariableFunction.admin_email = et_email.text.toString()
                        GlobalVariableFunction.admin_user_name = email_removed_domain
                    } else {
                        GlobalVariableFunction.showToast(this@LoginActivity, "Enter a valid details!")
                    }
                } else {
                    GlobalVariableFunction.showToast(
                        this@LoginActivity, "You are not an admin!"
                    )
                }
            }.addOnFailureListener {
                GlobalVariableFunction.showToast(this, "Failed")
            }
    }


    fun remove_domain_name_from_mail(email: String): String {
        val regex = Regex("(.+)@(.+)")
        val matchResult = regex.find(email)

        return matchResult?.let {
            // Group 1 contains the part before "@"
            it.groupValues[1]
        } ?: email
    }

    private fun openEmailClient() {
        val recipientEmail = "codeimagin@gmail.com"
        val subject = "Request for Admin Access - Art of Thoughts (Admin) App"
        val message =
            "Dear CodeImagin Support Teams,\n" + "\n" + "I hope this email finds you well. I am a user of the \"Art of Thoughts\" android application. I am writing to request admin access to the \"Art of Thoughts (Admin)\" android application. I assure you that I will use these privileges responsibly and contribute positively to the community." + "\n\n" + "Thank you for considering my request. I look forward to your response." + "\n\n" + "Best regards,\n" + "[Your Name]\n" + "[Your Contact Information]\n"

        // Create an intent with action ACTION_SENDTO
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:") // Only email apps should handle this

        // Set the recipient email address
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))

        // Set the subject
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)

        // Set the message body
        intent.putExtra(Intent.EXTRA_TEXT, message)
        startActivity(intent)
    }

}