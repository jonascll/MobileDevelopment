package com.example.carpool

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
    val executorService : ExecutorService = Executors.newFixedThreadPool(1)
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

        val runnable : Runnable = Runnable {
            handleMethod()
        }
        executorService.submit(runnable)
        val intent = Intent(this, MainPageActivity::class.java)
        startActivity(intent)
    }

    fun handleMethod() {
        val dbRefFirebase = FirebaseDatabase.getInstance().reference
        dbRefFirebase.child("Users").addListenerForSingleValueEvent(object : ValueEventListener {


            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("key tag" , snapshot.key.toString())

                    val poolerRequester = snapshot.child(requesterUid.toString()).getValue(Pooler::class.java)
                    if(poolerRequester != null) {
                        poolerRequester.isSearchingForPooler = false
                        dbRefFirebase.child("Users").child(requesterUid.toString()).setValue(poolerRequester)
                    }



                    val poolerPooler = snapshot.child(poolerUid.toString()).getValue(Pooler::class.java)
                    if(poolerPooler != null) {
                        poolerPooler.isPooler = false
                        dbRefFirebase.child("Users").child(poolerUid.toString()).setValue(poolerPooler)
                    }

                }


            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "acceptedDrivesDb"
        ).fallbackToDestructiveMigration().build()
        db.acceptedDriveDao().deleteWithRequestUidAndPoolerUid(requesterUid.toString(), poolerUid.toString())
    }


}