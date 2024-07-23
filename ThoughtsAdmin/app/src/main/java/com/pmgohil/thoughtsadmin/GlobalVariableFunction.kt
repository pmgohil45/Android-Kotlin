package com.pmgohil.thoughtsadmin

import android.content.Context
import android.widget.Toast

object GlobalVariableFunction {
    lateinit var admin_email: String
    lateinit var admin_user_name: String
    lateinit var admin_user_role: String//? = null

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}
