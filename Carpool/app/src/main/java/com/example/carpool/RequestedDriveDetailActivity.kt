package com.example.carpool

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.reflect.typeOf


class RequestedDriveDetailActivity : AppCompatActivity(){
    var email : String? = null
    var startCity : String? = null
    var startAddress : String? = null
    var endCity : String? = null
    var destination : String? = null
    var poolerUid : String? = null
    var requesterUid : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO : style layout correctly
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

    fun handleOnDeclineRequestClick(view: View) {
        val intent = Intent(this, MainPageActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun handleOnAcceptRequestClick(view: View) {

        //TODO : remove from firebaseDatabase when a requested drive becomes a accepted drive
        removeRequestedDriveFB()
        val runnable = Runnable {  putNewAcceptedDrive() }

        val thread = Thread(runnable)
        thread.start()
        thread.join()


    }

    fun putNewAcceptedDrive() {
        val acceptedDrive = AcceptedDrive()
        acceptedDrive.destination = destination.toString()
        acceptedDrive.email = email.toString()
        acceptedDrive.endCity = endCity.toString()
        acceptedDrive.id = 0
        acceptedDrive.poolerUid = poolerUid.toString()
        acceptedDrive.requesterUid = requesterUid.toString()
        acceptedDrive.startAddress = startAddress.toString()
        acceptedDrive.startCity = startCity.toString()

        val db = Room.databaseBuilder(
            baseContext,
            AppDatabase::class.java, "acceptedDrivesDb"
        ).fallbackToDestructiveMigration().build()
        val acceptedDriveEntity = AcceptedDriveEntity(
            0,
            email.toString(),
            requesterUid.toString(),
            poolerUid.toString(),
            startAddress.toString(),
            startCity.toString(),
            endCity.toString(),
            destination.toString()
        )
        db.acceptedDriveDao().insertNewAcceptedDrive(acceptedDriveEntity)
        val intent = Intent(this, MainPageActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        startActivity(intent)
        finish()
        }



//TODO fix issue with firebase multithread shit
    fun removeRequestedDriveFB() {
        val acceptedDrive = AcceptedDrive()
        acceptedDrive.destination = destination.toString()
        acceptedDrive.email = email.toString()
        acceptedDrive.endCity = endCity.toString()
        acceptedDrive.id = 0
        acceptedDrive.poolerUid = poolerUid.toString()
        acceptedDrive.requesterUid = requesterUid.toString()
        acceptedDrive.startAddress = startAddress.toString()
        acceptedDrive.startCity = startCity.toString()
        val firebaseDb = FirebaseDatabase.getInstance().reference
        firebaseDb.child("AcceptedDrives").child(UUID.randomUUID().toString()).setValue(acceptedDrive)
        firebaseDb.child("RequestedDrives").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var poolerUidExists = false
                var requesterUidExists = false

                snapshot.children.forEach{snapshotChild ->
                    snapshotChild.children.forEach{
                        if(it.key == "poolerUid" && !requesterUidExists && !poolerUidExists) {
                            poolerUidExists = it.value == poolerUid
                        }
                        if(it.key == "requesterUid" && poolerUidExists) {
                            requesterUidExists = it.value == requesterUid
                        }
                    }
                    if(poolerUidExists && requesterUidExists) {
                        firebaseDb.child("RequestedDrives").child(snapshotChild.key.toString()).removeValue()


                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                //TODO good code plx
            }
        })
    }
}