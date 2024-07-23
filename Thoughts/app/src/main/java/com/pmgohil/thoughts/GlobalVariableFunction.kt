package com.pmgohil.thoughts

import android.content.Context
import android.widget.Toast

object GlobalVariableFunction {
    lateinit var email: String
    lateinit var user_name: String
    lateinit var name: String
    lateinit var mail_id_image: String
    lateinit var internal_image: String

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
