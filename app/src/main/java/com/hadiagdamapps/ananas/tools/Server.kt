package com.hadiagdamapps.ananas.tools

import android.content.Context
import android.util.Log
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.lang.Exception

abstract class Server(private val context: Context) {

    private val serverUrl = ""
    private val notFoundKey = "not found"
    private val successKey = "success"
    private val invalidResponseException = Exception("invalid response")


    abstract fun onError(exception: Exception)


    fun checkLogin(
        model: LoginModel,
        onSuccess: (picture: String, name: String) -> Unit,
        onNotFound: () -> Unit,
    ) {


        Volley.newRequestQueue(context).add(
            StringRequest("$serverUrl/check-login?username=${model.username}&password=${model.password}",
                {

                    val response = JSONObject(it)

                    when (response.getString("status")) {

                        notFoundKey -> onNotFound()

                        successKey -> onSuccess(
                            response.getString("picture"),
                            response.getString("name")
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


}