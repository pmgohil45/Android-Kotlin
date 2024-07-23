package com.pmgohil.thoughts

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {
    companion object {
        private const val PREF_NAME = "SharedPreferencesManager"
        private const val KEY_IMAGE_URL = "imageUrl"
        private const val KEY_NAME = "name"
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    var imageUrl: String?
        get() = sharedPreferences.getString(KEY_IMAGE_URL, "")
        set(value) = editor.putString(KEY_IMAGE_URL, value).apply()

    var name: String?
        get() = sharedPreferences.getString(KEY_NAME, "")
        set(value) = editor.putString(KEY_NAME, value).apply()

    var username: String?
        get() = sharedPreferences.getString(KEY_USERNAME, "")
        set(value) = editor.putString(KEY_USERNAME, value).apply()

    var email: String?
        get() = sharedPreferences.getString(KEY_EMAIL, "")
        set(value) = editor.putString(KEY_EMAIL, value).apply()

    // Add other properties as needed

    // Commit changes
    fun save() {
        editor.apply()
    }

    // Clear all data from SharedPreferences
    fun clear() {
        editor.clear().apply()
    }
}