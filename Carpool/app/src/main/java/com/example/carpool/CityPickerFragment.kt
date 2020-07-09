package com.example.carpool

import android.os.Bundle
import android.text.Editable
import android.util.JsonReader
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.gson.JsonObject
import okhttp3.*
import okhttp3.internal.http2.Header
import okhttp3.internal.wait
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.StringReader

class CityPickerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val authToken = getAuthToken()
        return inflater.inflate(R.layout.fragment_citypicker,container,false)
    }
    fun getAuthToken() :String {

        var authToken : String = ""
        val client = OkHttpClient()
            val request =
                Request.Builder().url("https://www.universal-tutorial.com/api/getaccesstoken")
                    .addHeader(
                        "api-token",
                        "IN-lItbDbEN10iQlSIpzsJOgOHRdKNVsNQL-YOizjiYwZl45Ml74iJj4yM9aqU7w7cY"
                    ).addHeader("user-email", "11700223@student.pxl.be").build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("debugging tag", e.message.toString())

                }

                override fun onResponse(call: Call, response: Response) {
                    //TODO: need to use authToken to get all cities in each 'state' of belgium so i can list them in the spinner
                    Log.d("debugging tag", request.toString())
                    authToken = response.body!!.string()

                    authToken = authToken.substring(authToken.indexOf(':') + 1)
                    authToken = authToken.replace("\"", "")
                    authToken = authToken.replace("}", "")
                    //Log.d("debugging tag", response.body!!.string())
                    Log.d("debugging tag", authToken)
                    getAllGemeentes(authToken)


                }


            })
        return authToken

    }

    fun getAllGemeentes(authToken: String) {
        val gemeentes = ArrayList<String>()
        val client = OkHttpClient()
        val request2 = Request.Builder().url("https://www.universal-tutorial.com/api/states/Belgium")
            .addHeader("Authorization", "Bearer $authToken").build()
        client.newCall(request2).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("debugging tag", e.message.toString())

            }

            override fun onResponse(call: Call, response: Response) {
                //TODO: need to use authToken to get all cities in each 'state' of belgium so i can list them in the spinner
                Log.d("debugging tag", request2.toString())
                val responsebody = response.body

                /*authToken = authToken.substring(authToken.indexOf(':') + 1)
                authToken = authToken.replace("\"", "")
                authToken = authToken.replace("}", "")*/
                //Log.d("debugging tag", response.body!!.string())
                Log.d("debugging tag", responsebody.toString())
                Log.d("debugging tag", "testfunction")
                val body = responsebody?.string()
                val lines = body?.split(",")
                if (lines != null) {

                    for (line in lines) {
                        Log.d("debugging tag", line)
                        if(!line.contains("]")) {
                            gemeentes.add(line.substring(line.indexOf(':') + 2, line.length - 2))
                        } else {
                            gemeentes.add(line.substring(line.indexOf(':') + 2, line.length - 3))
                        }
                    }
                    Log.d("debugging tag" , gemeentes.toString())
                }


            }
        })
    }


}

