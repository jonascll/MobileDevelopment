package com.example.carpool

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class CityPickerActivity : AppCompatActivity() {
    private val authenticator = FirebaseAuth.getInstance()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser = authenticator.currentUser
        setContentView(R.layout.activity_citypicker)


    }

    fun handleClickOfContinueButton(view: View) {
        val intent = Intent(this, MainPageActivity::class.java)
        val fragment = supportFragmentManager.findFragmentById(R.id.cityPickerFragment) as CityPickerFragment
        val spinner = fragment.getSpinner()
        val pickedCityId = spinner?.selectedItemId
        intent.putExtra("pickedCityId", pickedCityId)
        startActivity(intent)
    }

    override fun onBackPressed() {

    }


    fun setSpinnerValue(){
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        var cityName : String? = null
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    if(location != null) {
                        val gcd = Geocoder(this, Locale.getDefault())
                        val addresses = gcd.getFromLocation(location.latitude, location.longitude, 100)
                        cityName = addresses[0].locality
                        val fragment = supportFragmentManager.findFragmentById(R.id.cityPickerFragment) as CityPickerFragment
                        val spinner = fragment.getSpinner()
                        val adapter = spinner?.adapter
                        if (adapter != null) {
                            for( i in adapter.count-1 downTo 0 ) {
                                val value = adapter.getItem(i)
                                if(value == cityName) {
                                    spinner.setSelection(i)
                                    break
                                }
                            }



                        }
                    }
                }
        }

    }

}