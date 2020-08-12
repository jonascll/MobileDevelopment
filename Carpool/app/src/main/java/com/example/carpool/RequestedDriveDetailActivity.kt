package com.example.carpool

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
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
        emailField.text = String.format(resources.getString(R.string.email_requested_drive_detail), email)
        startCityField.text = String.format(resources.getString(R.string.start_city_requested_drive_detail), startCity)
        startAddressField.text = String.format(resources.getString(R.string.start_address_requested_drive_detail), startAddress)
        endCityField.text = String.format(resources.getString(R.string.end_city_requested_drive_detail), endCity)
        destinationField.text = String.format(resources.getString(R.string.end_address_requested_drive_detail), destination)
        poolerUidField.text = String.format(resources.getString(R.string.pooler_uid_request_drive_detail), poolerUid)
        requesterUidField.text = String.format(resources.getString(R.string.requester_uid_request_drive_detail), requesterUid)
    }

    fun handleOnDeclineRequestClick(view: View) {
        val intent = Intent(this, MainPageActivity::class.java)
        startActivity(intent)
        finish()
    }
    fun handleOnAcceptRequestClick(view: View) {
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
        val runnable = Runnable { db.acceptedDriveDao().insertNewAcceptedDrive(acceptedDriveEntity) }
        val thread = Thread(runnable)
        thread.start()

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
                val toast = Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT)
                toast.show()
            }
        })
        val intent = Intent(this, MainPageActivity::class.java)
        startActivity(intent)
        finish()
        }

}