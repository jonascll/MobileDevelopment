package com.example.carpool

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class CityPickerActivity : AppCompatActivity() {
    private val authenticator = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser = authenticator.currentUser
        if(currentUser == null) {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }
        setContentView(R.layout.activity_citypicker)
        //TODO handle click of the continue button
    }
}