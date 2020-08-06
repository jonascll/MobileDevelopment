package com.example.carpool

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ShowRequestedDrivesActivity : AppCompatActivity() {
    var poolerUid : String? = null
    var requesterUid : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO make it so you cant see yourself by  hiding the pooler button and maybe showing another button that goes to a accept requests activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showrequesteddrives)
    }


    fun handleClickOnFoundRequest(view : View) {
        val intent = Intent(this, RequestedDriveDetailActivity::class.java)
        val emailField : TextView = findViewById(R.id.request_email)
        val startCityField : TextView = findViewById(R.id.request_start_city)
        val startAddressField : TextView = findViewById(R.id.request_start_address)
        val endCityField : TextView = findViewById(R.id.request_end_city)
        val destinationField : TextView = findViewById(R.id.request_destination)
        getPoolerUidAndRequesterUid(emailField) {
            if(it) {
                intent.putExtra("email", emailField.text.toString())
                intent.putExtra("startCity", startCityField.text.toString())
                intent.putExtra("startAddress", startAddressField.text.toString())
                intent.putExtra("endCity", endCityField.text.toString())
                intent.putExtra("destination", destinationField.text.toString())
                intent.putExtra("poolerUid", poolerUid)
                intent.putExtra("requesterUid", requesterUid)
                startActivity(intent)
                Log.d("start detail tag", "started detail Pog")
            }
        }
    }

    fun getPoolerUidAndRequesterUid(emailField : TextView, completion : (Boolean) -> Unit) {
        val ref = FirebaseDatabase.getInstance().reference
        ref.child("RequestedDrives").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    val request : RequestedDrive? = it.getValue(RequestedDrive::class.java)
                    if(request?.email == emailField.text) {
                        poolerUid = request?.poolerUid
                        requesterUid = request?.requesterUid
                    }
                }
                completion(true)
            }
            override fun onCancelled(error: DatabaseError) {
                completion(false)
                TODO("Not yet implemented")
            }
        })
    }
}