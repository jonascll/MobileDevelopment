package com.example.carpool

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import okhttp3.*
import okhttp3.internal.http2.Header
import org.json.JSONObject
import java.io.IOException

class CityPickerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getAllCittiesInBelgium()
        return inflater.inflate(R.layout.fragment_citypicker,container,false)
    }
    fun getAllCittiesInBelgium() {
        val client = OkHttpClient()
        val request = Request.Builder().url("https://www.universal-tutorial.com/api/getaccesstoken")
            .addHeader("api-token", "IN-lItbDbEN10iQlSIpzsJOgOHRdKNVsNQL-YOizjiYwZl45Ml74iJj4yM9aqU7w7cY" ).addHeader("user-email", "11700223@student.pxl.be").build()
        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("debugging tag", e.message.toString())

            }

            override fun onResponse(call: Call, response: Response) {
                //TODO: need to use authToken to get all cities in each 'state' of belgium so i can list them in the spinner
                Log.d("debugging tag", request.toString())
                var authToken = response.body!!.string()

                authToken = authToken.substring(authToken.indexOf(':') + 1)
                authToken = authToken.replace("\"", "")
                authToken = authToken.replace("}", "")
                //Log.d("debugging tag", response.body!!.string())
                Log.d("debugging tag", authToken)

            }

        })
    }


}

