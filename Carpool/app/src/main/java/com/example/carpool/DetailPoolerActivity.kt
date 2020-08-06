package com.example.carpool

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*


class DetailPoolerActivity : AppCompatActivity() {
    var startCityPooler: String? = null
    var startAddressPooler: String? = null
    var endCityPooler: String? = null
    var destinationPooler: String? = null
    var isPooler: Boolean? = null
    var startCity: String? = null
    var startAddress: String? = null
    var endCity: String? = null
    var destination: String? = null
    var uid: String? = null
    var uidPooler: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detailpooler)
        startCityPooler = intent.getStringExtra("startCityPooler")
        startAddressPooler = intent.getStringExtra("startAddressPooler")
        endCityPooler = intent.getStringExtra("endCityPooler")
        destinationPooler = intent.getStringExtra("endAddressPooler")
        uidPooler = intent.getStringExtra("uidPooler")
        isPooler = intent.getBooleanExtra("isPooler", true)
        startCity = intent.getStringExtra("startCity")
        startAddress = intent.getStringExtra("startAddress")
        endCity = intent.getStringExtra("endCity")
        destination = intent.getStringExtra("endAddress")
        uid = intent.getStringExtra("uid")
        setValues()


    }


    fun setValues() {
        val uidField: TextView = findViewById(R.id.detailUid)
        uidField.text = uid
        val detailStartCityField: TextView = findViewById(R.id.detailStartCity)
        detailStartCityField.text = startCityPooler
        val detailStartAddressField: TextView = findViewById(R.id.detailStartAddress)
        detailStartAddressField.text = startAddressPooler
        val detailEndCityField: TextView = findViewById(R.id.detailDestinationCity)
        detailEndCityField.text = endCityPooler
        val detailEndDestinationField: TextView = findViewById(R.id.detailDestination)
        detailEndDestinationField.text = destinationPooler
        val detailIsPoolerField: TextView = findViewById(R.id.detailIsPooler)
        detailIsPoolerField.text = isPooler.toString()
    }

    fun handleOnDeclinePoolerClick(view: View) {}
    //TODO fix firebase multithread or thread or whatever issue
    fun handleOnAcceptPoolerClick(view: View) {

        val driveRequest = RequestedDrive()
        if (startAddress != null && endCity != null && destination != null && startCity != null) {
            driveRequest.startCity = startCity.toString()
            driveRequest.endCity = endCity.toString()
            driveRequest.destination = destination.toString()
            driveRequest.startAddress = startAddress.toString()
            driveRequest.email = FirebaseAuth.getInstance().currentUser?.email.toString()
        }
        if (uid != null) {
            driveRequest.poolerUid = uidPooler.toString()
            driveRequest.requesterUid = uid.toString()
        }
        val poolerObject: Pooler = Pooler()
        poolerObject.startCity = startCity.toString()
        poolerObject.startAddress = startAddress.toString()
        poolerObject.endCity = endCity.toString()
        poolerObject.destinationAddress = destination.toString()
        poolerObject.isSearchingForPooler = true
        val ref = FirebaseDatabase.getInstance().reference
        ref.child("Users").child(uid.toString()).setValue(poolerObject)
        checkIfRequestHasAlreadyBeenMade(driveRequest) { alreadyExists ->
            Log.d("debugging tag", alreadyExists.toString())
            if (!alreadyExists) {
                ref.child("RequestedDrives").child(UUID.randomUUID().toString())
                    .setValue(driveRequest)
                    .addOnCompleteListener(
                        OnCompleteListener { task ->
                            if (task.isSuccessful) {
                                intent = Intent(this, MainPageActivity::class.java)
                                startActivity(intent)
                            }
                            //TODO : write code if it fails
                        })
            } else {
                intent = Intent(this, MainPageActivity::class.java)
                startActivity(intent)
            }
        }
    }


    fun checkIfRequestHasAlreadyBeenMade(
        request: RequestedDrive,
        alreadyExists: (Boolean) -> Unit
    ) {
        val ref = FirebaseDatabase.getInstance().reference
        var poolerUidExists = false
        var requesterUidExists = false
        ref.child("RequestedDrives").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{dataKey ->
                   for(data in dataKey.children){
                       if(data.key == "poolerUid") {
                           Log.d("debugging tag", data.toString())
                               if(!poolerUidExists && data.value == request.poolerUid) {
                                   poolerUidExists = true
                               }
                           }
                       if(data.key == "requesterUid") {
                           if(!requesterUidExists && data.value == request.requesterUid && poolerUidExists){
                               requesterUidExists = true
                           }
                       }
                    }
                }
                if(!requesterUidExists) {
                    alreadyExists(false)
                }
                if(requesterUidExists) {
                    alreadyExists(true)
                }

            }
            override fun onCancelled(error: DatabaseError) {
                //TODO implement

            }
            }

          )
        Log.d("debugging tag", requesterUidExists.toString())


    }
}

