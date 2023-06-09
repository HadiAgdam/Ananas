package com.hadiagdamapps.ananas.tools

import android.content.Context
import android.util.Log
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.lang.Exception

abstract class Server(private val context: Context) {

    private val serverUrl = ""
    private val notFoundKey = "not found"
    private val successKey = "success"
    private val usernameTakenKey = "taken"
    private val invalidResponseException = Exception("invalid response")


    abstract fun onError(exception: Exception)

    fun updateData() {

    }



    fun checkLogin(
        model: LoginModel,
        onSuccess: () -> Unit,
        onNotFound: () -> Unit,
    ) {


        Volley.newRequestQueue(context).add(
            StringRequest("$serverUrl/check_login?username=${model.username}&password=${model.password}",
                {

                    val response = JSONObject(it)

                    when (response.getString("status")) {

                        notFoundKey -> onNotFound()

                        successKey -> onSuccess(
                        )

                        else -> onError(invalidResponseException)
                    }


                },
                {
                    Log.e("check login error", it.toString())
                    onError(it)
                })
        )

    }



    fun checkUserTaken(username: String, onSuccess: () -> Unit, onTaken: () -> Unit) {


        Volley.newRequestQueue(context).add(StringRequest("$serverUrl/check_username?username$username",
            {


            when (JSONObject(it).getString("status")) {
                usernameTakenKey -> onTaken()

                successKey -> onSuccess()
            }

            },
            {
                onError(it)
                Log.e("check user volley error", it.toString())
            }))

    }

    fun register(user: UserModel, onSuccess: () -> Unit) {
        Volley.newRequestQueue(context).add(StringRequest("$serverUrl/register?username=${user.loginModel.username}&password=${user.loginModel.password}&name=${user.name}&picture=${user.picture}",
            {

                when(JSONObject(it).getString("status")) {

                    successKey -> onSuccess()

                }

            },
            {}))
    }


}