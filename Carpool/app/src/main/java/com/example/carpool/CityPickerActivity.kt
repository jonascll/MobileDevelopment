package com.example.carpool

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class CityPickerActivity : AppCompatActivity() {
    private val authenticator = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser = authenticator.currentUser
        setContentView(R.layout.activity_citypicker)
        //TODO handle click of the continue button

    }

    fun handleClickOfContinueButton(view: View) {
        val intent = Intent(this, MainPageActivity::class.java)
        startActivity(intent)
    }


}