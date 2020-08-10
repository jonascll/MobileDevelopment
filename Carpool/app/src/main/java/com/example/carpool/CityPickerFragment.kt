package com.example.carpool

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import okhttp3.*
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


class CityPickerFragment : Fragment(), LocationListener {
    val cities = ArrayList<String>()
    var location : Location? = null
    var cityName : String? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setCitiesOnSpinner()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity?.applicationContext!!)
        return inflater.inflate(R.layout.fragment_citypicker,container,false)
    }

    override fun onStart() {
        super.onStart()

    }
    private fun setCitiesOnSpinner () {

        var authToken : String
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
                    authToken = response.body!!.string()
                    authToken = authToken.substring(authToken.indexOf(':') + 1)
                    authToken = authToken.replace("\"", "")
                    authToken = authToken.replace("}", "")
                    getAllCitiesInGemeenteVlaamsBrabant(authToken)

                }
            })
    }



    fun getAllCitiesInGemeenteVlaamsBrabant(authToken: String) {
        val client = OkHttpClient()
            val request = Request.Builder().url("https://www.universal-tutorial.com/api/cities/Vlaams-Brabant")
                .addHeader("Authorization", "Bearer $authToken").build()
            client.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("debugging tag", e.message.toString())

                }


                override fun onResponse(call: Call, response: Response) {
                    val responsebody = response.body
                    val body = responsebody?.string()
                    val lines = body?.split(",")
                    if (lines != null) {

                        for (line in lines) {
                            if(!line.contains("]")) {
                                cities.add(line.substring(line.indexOf(':') + 2, line.length - 2))
                            } else {
                                cities.add(line.substring(line.indexOf(':') + 2, line.length - 3))
                            }
                        }

                    }

                    val mainHandler = Handler(context?.mainLooper!!)
                    val runnable = Runnable {
                        val adapter = ArrayAdapter<String>(activity!!.applicationContext, android.R.layout.simple_spinner_item, cities)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        val spinner = view?.findViewById<Spinner>(R.id.cityPicker)
                        spinner?.adapter = adapter
                        if(activity is SignUpAsPoolerActivity) {
                            val signUpActivity = activity as SignUpAsPoolerActivity
                            signUpActivity.setSpinnerValue()
                        }
                        if(activity is FindPoolerFormActivity) {
                            val signUpActivity = activity as FindPoolerFormActivity
                            signUpActivity.setSpinnerValue()
                        }
                    }
                    mainHandler.post(runnable)
                    getLocation()
                    if(location != null) {

                    }
                }
            })
    }

    fun getSpinner(): Spinner? {
        return view?.findViewById<Spinner>(R.id.cityPicker)
    }


   fun getLocation() {
       if (ActivityCompat.checkSelfPermission(
               activity!!.applicationContext,
               Manifest.permission.ACCESS_FINE_LOCATION
           ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
               activity!!.applicationContext,
               Manifest.permission.ACCESS_COARSE_LOCATION
           ) == PackageManager.PERMISSION_GRANTED
       ) {
           fusedLocationClient.lastLocation
               .addOnSuccessListener { location : Location? ->
                   if(location != null) {
                       val gcd = Geocoder(context, Locale.getDefault())
                       val addresses = gcd.getFromLocation(location.latitude, location.longitude, 100)
                       Log.d("addresses", addresses.toString())
                   }

               }
       }

   }
        //TODO fix location stuff so it gets your location
    override fun onLocationChanged(p0: Location) {
        location = p0
        val gcd = Geocoder(context, Locale.getDefault())
        val addresses = gcd.getFromLocation(p0.latitude, p0.longitude, 1)
        Log.d("addresses", addresses.toString())
        cityName = addresses[0].locality
    }


}



