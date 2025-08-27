/*
package com.example.taskmanager

import android.content.Context

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("session", Context.MODE_PRIVATE)

    fun saveLogin(email: String, username: String, imageuri: String) {
        prefs.edit().apply {
            putBoolean("is_log_in", true)
            apply()
        }
    }

    fun saveBooleanValues(key: String, value: Boolean) {
        prefs.edit().apply {
            putBoolean(key, value)
            apply()
        }
    }

    fun saveString(key: String, value: String) {
        prefs.edit().apply {
            putString(key, value)
            apply()
        }
    }

    fun isLoggedIn() : Boolean {
        return prefs.getBoolean("is_log_in", false)
    }

    fun getBooleanValues(key: String, value: Boolean) {
        prefs.getBoolean(key, value)
    }
    fun getEmail(key: String, value: String) = prefs.getString(key, value)
    fun getUsername() = prefs.getString("username", "")

    fun logout() {
        prefs.edit().clear().apply()
    }
}

*/
