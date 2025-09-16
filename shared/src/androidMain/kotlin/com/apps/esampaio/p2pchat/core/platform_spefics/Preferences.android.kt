package com.apps.esampaio.p2pchat.core.platform_spefics

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

//Preferences Android
actual class Preferences(context: Context) {
    private val sharedPreferences: SharedPreferences by lazy { // [2]
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE) // [3]
    }

    actual fun getString(key: String, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue) // [4]
    }

    actual fun setString(key: String, value: String) {
        sharedPreferences.edit() { // [5]
            putString(key, value)
            // [6]
        }
    }
}