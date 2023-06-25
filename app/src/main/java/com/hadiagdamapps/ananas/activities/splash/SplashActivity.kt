package com.hadiagdamapps.ananas.activities.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import com.hadiagdamapps.ananas.MainActivity
import com.hadiagdamapps.ananas.R
import com.hadiagdamapps.ananas.activities.account.login.LoginActivity
import com.hadiagdamapps.ananas.activities.account.register.RegisterActivity
import com.hadiagdamapps.ananas.tools.ActivityParent
import com.hadiagdamapps.ananas.tools.LoginModel
import com.hadiagdamapps.ananas.tools.Server
import java.lang.Exception

class SplashActivity : ActivityParent(R.layout.activity_splash) {

    private lateinit var textView: TextView
    private var model: LoginModel? = null

    private val tryAgainListener= OnClickListener {
        textView.visibility = View.INVISIBLE
    }


    private fun endToLogin() {
        data.clear()
        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
    }
    private fun endToMain() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
    }

    override fun volleyError(ex: Exception?) {
        textView.visibility = View.VISIBLE
    }


    private fun sendLogin() {
        server.checkLogin(
            model!!,
        onSuccess = {
            endToMain()
        },
        onNotFound = {endToLogin()})
    }



    override fun initialView() {
        textView = findViewById(R.id.tryAgainText)
        textView.setOnClickListener(tryAgainListener)
    }

    override fun main() {

        startActivity(Intent(this, RegisterActivity::class.java))

        return
        Handler().postDelayed(
            {
                val model = data.getLocalData()
                if (model == null)
                    endToLogin()

                this.model = model
                sendLogin()

            }, 2666)
    }


}
