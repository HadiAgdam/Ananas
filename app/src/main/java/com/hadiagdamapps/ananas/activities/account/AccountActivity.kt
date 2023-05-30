package com.hadiagdamapps.ananas.activities.account

import android.app.SearchManager.OnCancelListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.OnClickListener
import android.widget.Button
import com.hadiagdamapps.ananas.R
import com.hadiagdamapps.ananas.tools.ActivityParent

class AccountActivity : ActivityParent(R.layout.activity_account) {

    private lateinit var signUpButton: Button
    private lateinit var loginButton: Button


    private val signUpClick = OnClickListener {

    }

    private val loginOnclick = OnClickListener {

    }


    override fun initialView() {
        signUpButton = findViewById(R.id.signUpButton)
        signUpButton.setOnClickListener(signUpClick)

        loginButton = findViewById(R.id.loginButton)
        loginButton.setOnClickListener(loginOnclick)
    }

    override fun main() {

    }

}