package com.example.carpool

import android.content.Intent
import android.os.Bundle
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

class OnlineAcceptedDriveDetailActivity : AppCompatActivity() {

    var email: String? = null
    var requesterUid :  String? = null
    var poolerUid : String? = null
    var startAddress : String? = null
    var startCity : String? = null
    var endCity : String? = null
    var destination : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onlineaccepteddrivedetail)

        email = intent.getStringExtra("email")
        requesterUid = intent.getStringExtra("requesterUid")
        poolerUid = intent.getStringExtra("poolerUid")
        startAddress = intent.getStringExtra("startAddress")
        startCity = intent.getStringExtra("startCity")
        endCity = intent.getStringExtra("endCity")
        destination = intent.getStringExtra("destination")
        val emailField = findViewById<TextView>(R.id.onlineAcceptedDriveDetailEmail)
        val requesterUidField = findViewById<TextView>(R.id.onlineAcceptedDriveDetailRequesterUid)
        val poolerUidField = findViewById<TextView>(R.id.onlineAcceptedDriveDetailPoolerUid)
        val startAddressField = findViewById<TextView>(R.id.onlineAcceptedDriveDetailStartAddress)
        val startCityField = findViewById<TextView>(R.id.onlineAcceptedDriveDetailStartCity)
        val endCityField = findViewById<TextView>(R.id.onlineAcceptedDriveDetailEndCity)
        val destinationField = findViewById<TextView>(R.id.onlineAcceptedDriveDetailDestination)
        emailField.text = email
        requesterUidField.text = requesterUid
        poolerUidField.text = poolerUid
        startAddressField.text = startAddress
        startCityField.text = startCity
        endCityField.text = endCity
        destinationField.text = destination
    }

    fun handleEndDriveClick(view: View) {



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
        val runnable = Runnable { db.acceptedDriveDao().deleteWithRequestUidAndPoolerUid(requesterUid.toString(), poolerUid.toString()) }
        val thread = Thread(runnable)
        thread.start()

        val firebaseDb = FirebaseDatabase.getInstance().reference
        firebaseDb.child("AcceptedDrives").addListenerForSingleValueEvent(object : ValueEventListener {
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
                        firebaseDb.child("AcceptedDrives").child(snapshotChild.key.toString()).removeValue()


                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                val toast = Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT)
                toast.show()
            }
        })
        firebaseDb.child("Users").child(requesterUid.toString()).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val requester = snapshot.getValue(Pooler::class.java)
                requester?.isSearchingForPooler = false
                firebaseDb.child("Users").child(requesterUid.toString()).setValue(requester)
            }

            override fun onCancelled(error: DatabaseError) {
                val toast = Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT)
                toast.show()
            }
        })
        firebaseDb.child("Users").child(poolerUid.toString()).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val pooler = snapshot.getValue(Pooler::class.java)
                pooler?.isPooler = false
                firebaseDb.child("Users").child(poolerUid.toString()).setValue(pooler)
            }

            override fun onCancelled(error: DatabaseError) {
                val toast = Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT)
                toast.show()
            }
        })

        val intent = Intent(this, MainPageActivity::class.java)
        startActivity(intent)
    }




}