package com.hadiagdamapps.ananas.activities.account.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.hadiagdamapps.ananas.MainActivity
import com.hadiagdamapps.ananas.R
import com.hadiagdamapps.ananas.tools.ActivityParent
import com.hadiagdamapps.ananas.tools.LoginModel
import com.hadiagdamapps.ananas.tools.UserModel

class LoginActivity : ActivityParent(R.layout.activity_login) {

    private lateinit var usernameInput: TextInputEditText
    private lateinit var passwordInput: TextInputEditText
    private lateinit var errorText: TextView
    private lateinit var loginButton: Button


    private val loginListener = OnClickListener {
        loginButton.isEnabled = false

        val loginModel = LoginModel(usernameInput.text.toString(), passwordInput.text.toString())

        server.checkLogin(loginModel,
            {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()

            },
            {
                errorText.setText(R.string.user_not_found)
                loginButton.isEnabled = true
            })
    }

    override fun initialView() {
        usernameInput = findViewById(R.id.usernameInput)
        passwordInput = findViewById(R.id.passwordInput)
        errorText = findViewById(R.id.errorText)
        loginButton = findViewById(R.id.loginButton)
        loginButton.setOnClickListener(loginListener)
    }

    override fun main() {
        TODO("Not yet implemented")
    }
}