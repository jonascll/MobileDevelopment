package com.example.carpool

import android.content.Intent
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
        val intent = Intent(this, OnlineAcceptedDriveDetailActivity::class.java)
        intent.putExtra("email", email.text.toString())
        intent.putExtra("poolerUid", poolerUid.text.toString())
        intent.putExtra("requesterUid", requesterUid.text.toString())
        intent.putExtra("startCity", startCity.text.toString())
        intent.putExtra("endCity", endCity.text.toString())
        intent.putExtra("startAddress", startAddress.text.toString())
        intent.putExtra("destination", destination.text.toString())
        startActivity(intent)
    }
}