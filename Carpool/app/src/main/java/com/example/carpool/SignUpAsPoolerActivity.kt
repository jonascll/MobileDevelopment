package com.example.carpool

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.firebase.auth.FirebaseAuth

class SignUpAsPoolerActivity : AppCompatActivity() {
    var pickedCityId = 0L
    private val authenticator = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser = authenticator.currentUser

        setContentView(R.layout.activity_signupaspooler)
    }
    fun setSpinnerValue() {
        pickedCityId = intent.getLongExtra("pickedCityId", 0)
        val fragment = supportFragmentManager.findFragmentById(R.id.cityPickerSignUpPoolerStartCity) as CityPickerFragment
        val spinner = fragment.getSpinner()
        Log.d("debugging tag", spinner.toString())
        spinner?.setSelection(pickedCityId.toInt())
    }
    //TODO get the previously inputted city to this intent as an extra

}