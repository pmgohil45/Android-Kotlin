package com.pmgohil.thoughtsadmin

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.database.FirebaseDatabase

class SendAdminRequestActivity : AppCompatActivity() {
    lateinit var et_email_admin_request: EditText
    lateinit var et_password_admin_request: EditText
    lateinit var et_confirm_password_admin_request: EditText
    lateinit var btnRequestSubmit: Button
    lateinit var txt_password_result: TextView
    lateinit var txt_password_check: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_admin_request)
        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.color.transparent))
        try {
            et_email_admin_request = findViewById(R.id.et_email_admin_request)
            et_password_admin_request = findViewById(R.id.et_password_admin_request)
            et_confirm_password_admin_request = findViewById(R.id.et_confirm_password_admin_request)
            btnRequestSubmit = findViewById(R.id.btnRequestSubmit)
            txt_password_result = findViewById(R.id.txt_password_result)
            txt_password_check = findViewById(R.id.txt_password_check)


            et_password_admin_request.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    charSequence: CharSequence?, start: Int, before: Int, count: Int
                ) {
                }

                override fun afterTextChanged(editable: Editable?) {
                    val password = editable.toString()

                    if (isStrongPassword(password)) {
                        txt_password_check.visibility = View.VISIBLE
                        txt_password_check.text = "Password Strong"
                        txt_password_check.setTextColor(
                            ContextCompat.getColor(
                                this@SendAdminRequestActivity, R.color.black
                            )
                        )
                    } else {
                        txt_password_check.visibility = View.VISIBLE
                        txt_password_check.text =
                            "Password must have at least one uppercase letter, one lowercase letter, one number or digit, one symbol, and no spaces."
                        txt_password_check.setTextColor(
                            ContextCompat.getColor(
                                this@SendAdminRequestActivity, R.color.red
                            )
                        )
                    }
                }
            })

            et_confirm_password_admin_request.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence?, start: Int, count: Int, after: Int
                ) {
                }

                override fun onTextChanged(
                    charSequence: CharSequence?, start: Int, before: Int, count: Int
                ) {
                }

                override fun afterTextChanged(editable: Editable?) {
                    val password = et_password_admin_request.text.toString()
                    val confirmPassword = editable.toString()

                    if (isStrongPassword(confirmPassword)) {
                        txt_password_check.visibility = View.VISIBLE
                        txt_password_check.text = "Password Strong"
                        txt_password_check.setTextColor(
                            ContextCompat.getColor(
                                this@SendAdminRequestActivity, R.color.black
                            )
                        )
                    } else {
                        txt_password_check.visibility = View.VISIBLE
                        txt_password_check.text =
                            "Password must have at least one uppercase letter, one lowercase letter, one number or digit, one symbol, and no spaces."
                        txt_password_check.setTextColor(
                            ContextCompat.getColor(
                                this@SendAdminRequestActivity, R.color.red
                            )
                        )
                    }

                    if (password == confirmPassword) {

                        txt_password_result.visibility = View.VISIBLE
                        txt_password_result.text = "Password Match"
                        txt_password_result.setTextColor(
                            ContextCompat.getColor(
                                this@SendAdminRequestActivity, R.color.black
                            )
                        )
                    } else {
                        txt_password_result.visibility = View.VISIBLE
                        txt_password_result.text = "Password doesn't Match"
                        txt_password_result.setTextColor(
                            ContextCompat.getColor(
                                this@SendAdminRequestActivity, R.color.red
                            )
                        )
                    }
                }
            })

            btnRequestSubmit.setOnClickListener() {
                if (et_email_admin_request.text.toString().isNotEmpty()) {
                    if (isStrongPassword(et_password_admin_request.text.toString()) && et_confirm_password_admin_request.text.toString() == et_password_admin_request.text.toString()) {
                        insert_request_data()
                    } else {
                        GlobalVariableFunction.showToast(
                            this@SendAdminRequestActivity,
                            "Enter a valid password."
                        )
                    }
                } else {
                    GlobalVariableFunction.showToast(this, "Please fill above details!")
                }
            }

        } catch (e: Exception) {
            GlobalVariableFunction.showToast(this, e.toString())
        }
    }

    private fun insert_request_data() {
        try {
            if (et_email_admin_request.text.toString()
                    .isNotEmpty() && et_password_admin_request.text.toString()
                    .isNotEmpty() && et_confirm_password_admin_request.text.toString().isNotEmpty()
            ) {
                val database = FirebaseDatabase.getInstance()
                val reference = database.reference
                val user_name = remove_domain_name_from_mail(et_email_admin_request.text.toString())
                reference.child("admin_request").child(user_name).setValue(
                    AdminRequestData(
                        et_email_admin_request.text.toString(),
                        et_password_admin_request.text.toString(),
                        et_confirm_password_admin_request.text.toString(),
                        user_name.toString()
                    )
                ).addOnSuccessListener {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    GlobalVariableFunction.showToast(
                        this, "Your Admin Request will approved with in 7 Days."
                    )
                }.addOnFailureListener {
                    GlobalVariableFunction.showToast(this, "Something Wrong!")
                }
            }
        } catch (e: Exception) {
            GlobalVariableFunction.showToast(this, "$e!")
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

    fun isStrongPassword(password: String): Boolean {
        // Check for at least one uppercase letter
        val hasUpperCase = password.any { it.isUpperCase() }

        // Check for at least one lowercase letter
        val hasLowerCase = password.any { it.isLowerCase() }

        // Check for at least one digit
        val hasDigit = password.any { it.isDigit().not() }

        // Check for at least one symbol
        val hasSymbol = password.any { it.isLetterOrDigit().not() }

        // Check if the password doesn't contain spaces
        val hasNoSpaces = !password.contains(" ")

        // Return true if all criteria are met
        return hasUpperCase && hasLowerCase && hasDigit && hasSymbol && hasNoSpaces
    }

}