package com.example.carpool

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FindPoolerActivity : AppCompatActivity() {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val dbReference = FirebaseDatabase.getInstance().reference
    var pooler : Pooler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO make it so you cant see yourself by  hiding the pooler button and maybe showing another button that goes to a accept requests activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_findpooler)

    }

    fun getChosenPooler(uidText : String, completion : (Boolean) -> Unit) {

        dbReference.child("Users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    if(it.key == uidText) {
                        pooler = it.getValue(Pooler::class.java)
                    }

                }

                completion(true)

            }

            override fun onCancelled(error: DatabaseError) {
                completion(false)
            }
        })
    }

    fun handleClickOnFoundPooler(view : View) {
        //TODO find way to add the request of this user to the clicked pooler
        val uidField : TextView = view.findViewById(R.id.hidden_uid)
        val startAddressField : TextView = view.findViewById(R.id.person_startAddress)
        val endCityField : TextView = view.findViewById(R.id.person_endCity)
        val destinationField : TextView = view.findViewById(R.id.person_endAddress)
        val uidText = uidField.text
        var startAddres = startAddressField.text
        startAddres = startAddres.substring(startAddres.indexOf(':') + 2, startAddres.length)
        var endCity = endCityField.text
        endCity =  endCity.substring(endCity.indexOf(':') + 2, endCity.length)
        var destination = destinationField.text
        destination = destination.substring(destination.indexOf(':') + 2, destination.length)
        val driveRequest = RequestedDrive()
        driveRequest.destination = destination.toString()
        driveRequest.endCity = endCity.toString()
        driveRequest.startAddress = startAddres.toString()
        getChosenPooler(uidText.toString()){ completed ->
            if(completed) {
                pooler?.isSearchingForPooler = true
                dbReference.child("RequestedDrives").child(uidText.toString()).setValue(driveRequest)
                dbReference.child("Users").child(uidText.toString()).setValue(pooler).addOnCompleteListener(
                    OnCompleteListener { task ->
                        if(task.isSuccessful) {
                            val intent = Intent(this, MainPageActivity::class.java)
                            startActivity(intent)
                        }

                        if(task.isCanceled) {
                            Log.d("exception tag", task.exception?.message.toString())
                        }
                    })
            }
        }



    }

}