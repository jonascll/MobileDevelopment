package com.example.carpool

import android.content.Intent
import android.os.Bundle
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
    var startCity: String? = null
    var startAddress : String? = null
    var endCity : String? = null
    var destination : String? = null
    var isPooler : Boolean? = null
    var uid : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detailpooler)
        startCity = intent.getStringExtra("startCity")
        startAddress = intent.getStringExtra("startAddress")
        endCity = intent.getStringExtra("endCity")
        destination = intent.getStringExtra("endAddress")
        isPooler = intent.getBooleanExtra("isPooler", true)
        uid = intent.getStringExtra("uid")
        setValues()
    }



    fun setValues() {
        val uidField : TextView = findViewById(R.id.detailUid)
        uidField.text = uid
        val detailStartCityField : TextView = findViewById(R.id.detailStartCity)
        detailStartCityField.text = startCity
        val detailStartAddressField : TextView = findViewById(R.id.detailStartAddress)
        detailStartAddressField.text = startAddress
        val detailEndCityField : TextView = findViewById(R.id.detailDestinationCity)
        detailEndCityField.text = endCity
        val detailEndDestinationField : TextView = findViewById(R.id.detailDestination)
        detailEndDestinationField.text = destination
        val detailIsPoolerField : TextView = findViewById(R.id.detailIsPooler)
    }

    fun handleOnDeclinePoolerClick(view: View) {}
    fun handleOnAcceptPoolerClick(view: View) {

        val driveRequest = RequestedDrive()
        if (startAddress != null && endCity != null && destination != null) {
            driveRequest.startAddress = startCity.toString()
            driveRequest.endCity = endCity.toString()
            driveRequest.destination = destination.toString()
        }
        if (uid != null) {
            driveRequest.poolerUid = uid.toString()
        }
        val poolerObject : Pooler = Pooler()
        poolerObject.isPooler = isPooler as Boolean
        poolerObject.startCity = startCity.toString()
        poolerObject.startAddress = startAddress.toString()
        poolerObject.endCity = endCity.toString()
        poolerObject.destinationAddress = destination.toString()
        //TODO : add check to see if request already exists
        val ref = FirebaseDatabase.getInstance().reference
        ref.child("Users").child(uid.toString()).setValue(poolerObject)

        ref.child("RequestedDrives").child(UUID.randomUUID().toString()).setValue(driveRequest).addOnCompleteListener(
            OnCompleteListener {
                if(it.isSuccessful) {
                    intent = Intent(this, MainPageActivity::class.java)
                    startActivity(intent)
                }
            })
    }
}