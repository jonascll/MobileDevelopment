package com.example.carpool

import android.os.Bundle
import android.widget.TextView
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
        val emailField : TextView = findViewById(R.id.emailRequestedDriveDetail)
        val startCityField : TextView = findViewById(R.id.startCityRequestedDriveDetail)
        val startAddressField : TextView = findViewById(R.id.startAddressRequestedDriveDetail)
        val endCityField : TextView = findViewById(R.id.endCityRequestedDriveDetail)
        val destinationField : TextView = findViewById(R.id.endAddressRequestedDriveDetail)
        val poolerUidField : TextView = findViewById(R.id.poolerUidRequestedDriveDetail)
        val requesterUidField : TextView = findViewById(R.id.requesterUidRequestedDriveDetail)
        emailField.text = email
        startCityField.text = startCity
        startAddressField.text = startAddress
        endCityField.text = endCity
        destinationField.text = destination
        poolerUidField.text = poolerUid
        requesterUidField.text = requesterUid
    }
}