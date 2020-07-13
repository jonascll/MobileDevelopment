package com.example.carpool

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class FindPoolerFormActivity : AppCompatActivity() {
        var pickedCityId = 0L
        private val authenticator = FirebaseAuth.getInstance()
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val currentUser = authenticator.currentUser
            setContentView(R.layout.activity_findpoolerform)
        }
    fun setSpinnerValue() {
        pickedCityId = intent.getLongExtra("pickedCityId", 0)
        val fragment = supportFragmentManager.findFragmentById(R.id.cityPickerFindPoolerFragment) as CityPickerFragment
        val spinner = fragment.getSpinner()
        Log.d("debugging tag", spinner.toString())
        spinner?.setSelection(pickedCityId.toInt())
    }
}