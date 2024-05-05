package com.example.ecommerce_client

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesManager(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun saveString(key: String, value: String) {
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }

    fun saveInt(key: String, value: Int) {
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun saveBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun remove(key: String) {
        editor.remove(key)
        editor.apply()
    }

    fun clear() {
        editor.clear()
        editor.apply()
    }
}
