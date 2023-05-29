package com.hadiagdamapps.ananas.tools

import android.content.Context
import android.content.SharedPreferences

class Data(private val context: Context) {

    private val mode = 0
    private val loginKey = "user"

    fun getLocalData(): LoginModel? {

        val preferences = context.getSharedPreferences(loginKey, mode)

        val username = preferences.getString("username", null)
        val password = preferences.getString("password", null)

        if (username == null || password == null)
            return null

        return LoginModel(username, password)
    }


    fun updateLocalData(loginModel: LoginModel, name: String, picture: String) {
        val editor = context.getSharedPreferences(loginKey, mode).edit()

        editor.putString("username", loginModel.username)
        editor.putString("password", loginModel.password)
        editor.putString("name", name)
        editor.putString("picture", picture)

        editor.apply()
    }

    fun updateLocalData(picture: String, name: String) {

        val editor = context.getSharedPreferences(loginKey, mode).edit()

        editor.putString("picture", picture)
        editor.putString("name", name)

        editor.apply()

    }

    fun clear() {
        val editor = context.getSharedPreferences(loginKey, mode).edit()

        editor.clear()

        editor.apply()
    }


}