package com.example.carpool

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ShowRequestedDrivesActivity : AppCompatActivity() {
    var poolerUid : String? = null
    var requesterUid : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
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
        val email = emailField.text.toString().removeRange(0, emailField.text.toString().indexOf(':') + 1).trim()
        val startCity = startCityField.text.toString().removeRange(0, startCityField.text.toString().indexOf(':') + 1 ).trim()
        val startAddress = startAddressField.text.toString().removeRange(0, startAddressField.text.toString().indexOf(':') + 1).trim()
        val endCity = endCityField.text.toString().removeRange(0, endCityField.text.toString().indexOf(':') + 1).trim()
        val destination = destinationField.text.toString().removeRange(0, destinationField.text.toString().indexOf(':') + 1).trim()
        getPoolerUidAndRequesterUid(email) {
            if(it) {
                intent.putExtra("email", email)
                intent.putExtra("startCity", startCity)
                intent.putExtra("startAddress", startAddress)
                intent.putExtra("endCity", endCity)
                intent.putExtra("destination", destination)
                intent.putExtra("poolerUid", poolerUid)
                intent.putExtra("requesterUid", requesterUid)
                startActivity(intent)
                Log.d("start detail tag", "started detail Pog")
            }
        }
    }

    fun getPoolerUidAndRequesterUid(email : String, completion : (Boolean) -> Unit) {
        val ref = FirebaseDatabase.getInstance().reference
        ref.child("RequestedDrives").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    val request : RequestedDrive? = it.getValue(RequestedDrive::class.java)
                    if(request?.email == email) {
                        poolerUid = request?.poolerUid
                        requesterUid = request?.requesterUid
                    }
                }
                completion(true)
            }
            override fun onCancelled(error: DatabaseError) {
                val toast = Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT)
                toast.show()
            }
        })
    }
}