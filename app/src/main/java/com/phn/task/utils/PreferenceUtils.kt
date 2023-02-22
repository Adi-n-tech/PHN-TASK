package com.phn.task.utils

import android.content.Context

class PreferenceUtils(context: Context) {
    val sharedPreference = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

    fun putString(key: String, value: String) {
        var editor = sharedPreference.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String): String? {
        return sharedPreference.getString(key, "")
    }
}