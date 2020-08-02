package com.example.carpool

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ShowOnlineAcceptedDriveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showonlineaccepteddrives)
    }

    fun handleOnlineAcceptedDriveClick(view : View) {
        val email = findViewById<TextView>(R.id.emailOnlineDrive)
        val poolerUid = findViewById<TextView>(R.id.hidden_pooler_uid_online)
        val requesterUid = findViewById<TextView>(R.id.hidden_requester_uid_online)
        val startCity = findViewById<TextView>(R.id.startCityOnlineDrive)
        val endCity = findViewById<TextView>(R.id.endCityOnlineDrive)
        val startAddress = findViewById<TextView>(R.id.startAddressOnlineDrive)
        val destination = findViewById<TextView>(R.id.destinationOnlineDrive)
        //TODO : pass on these values with the intent and make a drive done that removes is looking for pooler and ispooler and the request
    }
}