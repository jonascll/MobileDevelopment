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
import java.util.*

class FindPoolerActivity : AppCompatActivity() {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val dbReference = FirebaseDatabase.getInstance().reference
    var pooler : Pooler? = null
    var endAddress : String? = null
    var startAddress : String? = null
    var startCity : String? = null
    var endCity : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO instead of instanty accepting a pooler by clicking on him make it go to a detail page
        //TODO make it so you cant see yourself by  hiding the pooler button and maybe showing another button that goes to a accept requests activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_findpooler)
        endAddress = intent.getStringExtra("endAddress")
        endCity = intent.getStringExtra("endCity")
        startCity = intent.getStringExtra("startCity")
        startAddress = intent.getStringExtra("startAddress")

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
        val uidField : TextView = view.findViewById(R.id.hidden_uid)
        val uidText = uidField.text
        getChosenPooler(uidText.toString()){
            if(it) {
                val intent = Intent(this, DetailPoolerActivity::class.java)
                intent.putExtra("endAddress", endAddress.toString())
                intent.putExtra("endAddressPooler", pooler?.destinationAddress)
                intent.putExtra("startAddress", startAddress.toString())
                intent.putExtra("startAddressPooler", pooler?.startAddress)
                intent.putExtra("endCity", endCity.toString())
                intent.putExtra("endCityPooler", pooler?.endCity)
                intent.putExtra("startCity", startCity.toString())
                intent.putExtra("startCityPooler", pooler?.startCity)
                intent.putExtra("isPooler", pooler?.isPooler)
                intent.putExtra("uidPooler", uidText.toString())
                intent.putExtra("uid", currentUser?.uid.toString())
                startActivity(intent)
            }
        }
    }
}


