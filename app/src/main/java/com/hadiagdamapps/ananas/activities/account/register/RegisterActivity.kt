package com.hadiagdamapps.ananas.activities.account.register

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.core.widget.doOnTextChanged
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.hadiagdamapps.ananas.R
import com.hadiagdamapps.ananas.tools.ActivityParent
import com.hadiagdamapps.ananas.tools.LoginModel
import com.hadiagdamapps.ananas.tools.PagerAdapter
import com.hadiagdamapps.ananas.tools.UserModel
import com.hadiagdamapps.ananas.tools.Validator

class RegisterActivity : ActivityParent(R.layout.activity_register) {

    private lateinit var pager: ViewPager2
    private lateinit var nextButton: Button
    private val layoutList =
        arrayListOf(R.layout.username_layout, R.layout.password_layout, R.layout.name_photo_layout)
    private var username = ""
    private var password = ""
    private var imageUri: Uri? = null

    private fun pick() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {

            when (resultCode) {

                RESULT_OK -> {
                    findViewById<ImageView>(R.id.imageView).setImageURI(data?.data)
                    imageUri = data?.data
                }

            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Log.e("position", "on request permission result")

        // not shown = -1
        // denied = -1
        // allowed = 0


        Log.e("result", grantResults[0].toString())

        if (requestCode == 1) {

            if (grantResults[0] == 0) {
                Log.e("position", "result ok")

                pick()

            } else {

                AlertDialog.Builder(this).apply {

                    setTitle("Permission Denied")
                    setMessage("In settings,\nallow access to files and media.")
                    setNeutralButton("DISMISS", null)
                    setPositiveButton("OPEN") { _, _ ->

                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivity(intent)


                    }

                }.show()

            }

        }
    }

    private fun askPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            1
        )
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }


    private fun end() {}


    override fun onBackPressed() {
        if (pager.currentItem > 0) pager.currentItem--
    }

    private val onNextClick = OnClickListener {
        val view = pager[0]

        when (pager.currentItem) {

            0 -> {

                val container: TextInputLayout = view.findViewById(R.id.usernameContainer)
                val input: TextInputEditText = view.findViewById(R.id.usernameInput)
                val username = input.text.toString()

//                input.doOnTextChanged { _, _, _, _ ->
//                    container.isErrorEnabled = false
//                }

                if (!Validator.isValidUsername(username)) {
                    container.error = resources.getString(R.string.username_invalid)
                    return@OnClickListener
                }

                nextButton.isEnabled = false
                server.checkUserTaken(username,
                    {
                        nextButton.isEnabled = true
                        this.username = username
                        pager.currentItem = 1
                    },
                    {
                        nextButton.isEnabled = true
                        container.error = resources.getString(R.string.username_taken)
                    })

            }

            1 -> {

                val passwordContainer: TextInputLayout = view.findViewById(R.id.passwordContainer)
                val passwordConfirmContainer: TextInputLayout =
                    view.findViewById(R.id.passwordConfirmContainer)

                val passwordInput: TextInputEditText = view.findViewById(R.id.passwordInput)
                val passwordConfirmInput: TextInputEditText =
                    view.findViewById(R.id.passwordConfirmInput)

                if (!Validator.isValidPassword(passwordInput.text.toString())) {
                    passwordContainer.error = resources.getString(R.string.password_invalid)
                    return@OnClickListener
                }

                if (passwordConfirmInput.text.toString() != passwordInput.text.toString()) {
                    passwordConfirmContainer.error =
                        resources.getString(R.string.password_not_match)
                    return@OnClickListener
                }


                password = passwordInput.text.toString()
                pager.currentItem = 2


                pager[0].findViewById<CardView>(R.id.box).setOnClickListener {
                    if (checkPermission()) pick()
                    else askPermission()
                }
                pager[0].findViewById<TextInputEditText>(R.id.nameInput).setText(username)
            }

            2 -> {

                val nameInput: TextInputEditText = view.findViewById(R.id.nameInput)
                val nameContainer: TextInputLayout = view.findViewById(R.id.nameContainer)


                if (!Validator.isValidName(nameInput.text.toString())) {
                    nameContainer.error = resources.getString(R.string.name_invalid)
                    return@OnClickListener
                }


                nextButton.isEnabled = false
                server.register(
                    UserModel(
                        LoginModel(username, password),
                        imageUri.toString(),
                        nameInput.text.toString()
                    )
                ) {
                    end()
                }

            }

        }

    }


    override fun initialView() {
        pager = findViewById(R.id.pager)
        nextButton = findViewById(R.id.nextButton)
        nextButton.setOnClickListener(onNextClick)
    }

    override fun main() {
        val adapter = PagerAdapter(layoutList, supportFragmentManager, lifecycle)
        pager.adapter = adapter
    }
}