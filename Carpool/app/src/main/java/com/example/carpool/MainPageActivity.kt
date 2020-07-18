package com.example.carpool


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
//TODO : fix slow button hiding if you are already signed up as a pooler
class MainPageActivity : AppCompatActivity() {
    private val authenticator = FirebaseAuth.getInstance()
    private val databaseReference = FirebaseDatabase.getInstance().reference

    var pickedCityId = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser = authenticator.currentUser
        pickedCityId = intent.getLongExtra("pickedCityId", 0)
        checkIfCurrentUserIsPooler()

        setContentView(R.layout.activity_mainpage)

    }

    fun handleSignUpPoolerClick(view: View) {
        val intent = Intent(this, SignUpAsPoolerActivity::class.java)
        intent.putExtra("pickedCityId", pickedCityId)
        startActivity(intent)
    }

    fun handleFindPoolerClick(view: View) {
        val intent = Intent(this, FindPoolerFormActivity::class.java)
        intent.putExtra("pickedCityId", pickedCityId)
        startActivity(intent)
    }

    fun checkIfCurrentUserIsPooler() {
        databaseReference.child("Users").child(authenticator.currentUser!!.uid).child("pooler")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val isPooler: Boolean? = snapshot.getValue(Boolean::class.java)
                    if (isPooler!!) {
                        val button: Button = findViewById<Button>(R.id.signupPoolerButton)
                        button.visibility = View.INVISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("debugging tag", error.message)
                }
            })

    }


}