package com.example.carpool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RequestedDriveDetailActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requesteddrivedetail)
        val email = intent.getStringExtra("email")
        val startCity = intent.getStringExtra("startCity")
        val startAddress = intent.getStringExtra("startAddress")
        val endCity = intent.getStringExtra("endCity")
        val destination = intent.getStringExtra("destination")
        val poolerUid = intent.getStringExtra("poolerUid")
        val requesterUid = intent.getStringExtra("requesterUid")
        //TODO : implement layout correctly
    }
}