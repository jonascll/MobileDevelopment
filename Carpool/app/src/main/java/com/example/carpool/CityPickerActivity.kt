package com.example.carpool

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class CityPickerActivity : AppCompatActivity() {
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