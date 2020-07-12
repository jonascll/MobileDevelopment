package com.example.carpool


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainPageActivity : AppCompatActivity() {
    private val authenticator = FirebaseAuth.getInstance()
    var pickedCityId = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser = authenticator.currentUser
        pickedCityId = intent.getLongExtra("pickedCityId", 0)
        setContentView(R.layout.activity_mainpage)
    }

    fun handleSignUpPoolerClick(view: View) {
        val intent = Intent(this, SignUpAsPoolerActivity::class.java)
        intent.putExtra("pickedCityId", pickedCityId)
        startActivity(intent)
    }
    fun handleFindPoolerClick(view: View) {}
}