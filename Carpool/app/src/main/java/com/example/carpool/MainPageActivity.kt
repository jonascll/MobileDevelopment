package com.example.carpool


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
class MainPageActivity : AppCompatActivity() {
    private val authenticator = FirebaseAuth.getInstance()
    private val databaseReference = FirebaseDatabase.getInstance().reference

    var pickedCityId = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("mainactivity tag", "started this piece of shit")
        val currentUser = authenticator.currentUser
        pickedCityId = intent.getLongExtra("pickedCityId", 0)
        checkIfCurrentUserIsPoolerOrLookingForPoolers()
        setContentView(R.layout.activity_mainpage)
    }

    override fun onBackPressed() {
        val intent = Intent(this, CityPickerActivity::class.java)
        startActivity(intent)
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

    fun checkIfCurrentUserIsPoolerOrLookingForPoolers() {
            val currentUser = authenticator.currentUser
            databaseReference.child("Users").child(currentUser?.uid.toString()).child("pooler")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val isPooler: Boolean? = snapshot.getValue(Boolean::class.java)
                        if (isPooler != null) {
                            if (isPooler) {
                                val buttonPooler: Button =
                                    findViewById<Button>(R.id.signupPoolerButton)
                                buttonPooler.visibility = View.INVISIBLE
                                val buttonFind: Button = findViewById(R.id.findPoolerButton)
                                buttonFind.visibility = View.INVISIBLE
                                val buttonRequests: Button = findViewById(R.id.seeRequests)
                                buttonRequests.visibility = View.VISIBLE
                                val buttonAcceptedDrives = findViewById<Button>(R.id.seeAcceptedDrives)
                                buttonAcceptedDrives.visibility = View.VISIBLE
                            } else {
                                val buttonPooler: Button =
                                    findViewById<Button>(R.id.signupPoolerButton)
                                buttonPooler.visibility = View.VISIBLE
                                val buttonFind: Button = findViewById(R.id.findPoolerButton)
                                buttonFind.visibility = View.VISIBLE
                                val buttonRequests: Button = findViewById(R.id.seeRequests)
                                buttonRequests.visibility = View.INVISIBLE
                                val buttonAcceptedDrives = findViewById<Button>(R.id.seeAcceptedDrives)
                                buttonAcceptedDrives.visibility = View.INVISIBLE
                            }
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("debugging tag", error.message)

                    }
                })
            databaseReference.child("Users").child(currentUser?.uid.toString())
                .child("searchingForPooler")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val isLookingForPooler = snapshot.getValue(Boolean::class.java)
                        if (isLookingForPooler != null) {
                            if (isLookingForPooler) {
                                val buttonPooler: Button =
                                    findViewById<Button>(R.id.signupPoolerButton)
                                buttonPooler.visibility = View.INVISIBLE
                                val buttonFind: Button = findViewById(R.id.findPoolerButton)
                                buttonFind.visibility = View.INVISIBLE
                            }
                        }

                    }

                    override fun onCancelled(error: DatabaseError) {
                        val toast = Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT)
                        toast.show()
                    }
                })

    }



    fun handleSeeRequests(view: View) {
        val intent = Intent(this, ShowRequestedDrivesActivity::class.java)
        startActivity(intent)
    }

    fun handleSeeAcceptedDrives(view: View) {
        val intent = Intent(this, ShowOnlineAcceptedDriveActivity::class.java)
        startActivity(intent)

    }


}