package com.example.carpool

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class RequestedDriveDetailActivity : AppCompatActivity(){
    var email : String? = null
    var startCity : String? = null
    var startAddress : String? = null
    var endCity : String? = null
    var destination : String? = null
    var poolerUid : String? = null
    var requesterUid : String? = null
    val executorService : ExecutorService = Executors.newFixedThreadPool(1)

    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO : style layout correctly
        //TODO Way of getting device id is wrong FIX
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_requesteddrivedetail)

        email = intent.getStringExtra("email")
        startCity = intent.getStringExtra("startCity")
        startAddress = intent.getStringExtra("startAddress")
        endCity = intent.getStringExtra("endCity")
        destination = intent.getStringExtra("destination")
        poolerUid = intent.getStringExtra("poolerUid")
        requesterUid = intent.getStringExtra("requesterUid")
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

    fun handleOnDeclineRequestClick(view: View) {}
    fun handleOnAcceptRequestClick(view: View) {
        //TODO : also put it in the firebase database just so when you log in you can see them for your account only (since if someone else logs in on your device it wont work)
        val runnable : Runnable = Runnable {
            putNewAcceptedDrive()
        }
        executorService.submit(runnable)
        val intent = Intent(this, MainPageActivity::class.java)
        startActivity(intent)


    }



    fun putNewAcceptedDrive() {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "acceptedDrivesDb"
        ).fallbackToDestructiveMigration().build()
        val acceptedDriveEntity = AcceptedDriveEntity(0,
            email.toString(),requesterUid.toString(),poolerUid.toString()
            ,startAddress.toString(), startCity.toString(), endCity.toString(), destination.toString())
        db.acceptedDriveDao().insertNewAcceptedDrive(acceptedDriveEntity)
        val firebaseDb = FirebaseDatabase.getInstance().reference
        firebaseDb.child("AcceptedDrives").child(UUID.randomUUID().toString()).setValue(acceptedDriveEntity)
    }
}