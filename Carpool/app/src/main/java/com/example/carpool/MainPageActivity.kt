package com.example.carpool


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainPageActivity : AppCompatActivity() {
    private val authenticator = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser = authenticator.currentUser
        Log.d("debugging tag", currentUser.toString())

        setContentView(R.layout.activity_mainpage)
        //TODO implement button click handlers
    }

    fun handleSignUpPoolerClick(view: View) {}
    fun handleFindPoolerClick(view: View) {}
}