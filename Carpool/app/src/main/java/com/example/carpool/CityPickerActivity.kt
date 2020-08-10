package com.example.carpool

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth

class CityPickerActivity : AppCompatActivity() {
    //TODO : check if user hasnt already become a pooler or is looking for a pooler cause then this is useless
    private val authenticator = FirebaseAuth.getInstance()
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




}