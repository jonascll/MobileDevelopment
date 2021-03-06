package com.example.carpool

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
class SignUpAsPoolerActivity : AppCompatActivity() {
    var pickedCityId = 0L
    private val authenticator = FirebaseAuth.getInstance()
    private val myRef = FirebaseDatabase.getInstance().reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser = authenticator.currentUser
        setContentView(R.layout.activity_signupaspooler)
    }
    fun setSpinnerValue() {
        pickedCityId = intent.getLongExtra("pickedCityId", 0)
        val fragment = supportFragmentManager.findFragmentById(R.id.cityPickerSignUpPoolerStartCity) as CityPickerFragment
        val spinner = fragment.getSpinner()
        Log.d("debugging tag", spinner.toString())
        spinner?.setSelection(pickedCityId.toInt())
    }
    fun handleSignUp(view: View) {
        val startFragment = supportFragmentManager.findFragmentById(R.id.cityPickerSignUpPoolerStartCity) as CityPickerFragment
        val startSpinner = startFragment.getSpinner()
        val startCity : String = startSpinner?.selectedItem.toString()
        val endFragment = supportFragmentManager.findFragmentById(R.id.signupPoolerEndCity) as CityPickerFragment
        val endSpinner = endFragment.getSpinner()
        val endCity = endSpinner?.selectedItem.toString()
        val destinationInput : EditText = findViewById<EditText>(R.id.signupPoolerEndAddress)
        val destination : String = destinationInput.text.toString()
        if((startCity != "") && (endCity != "") && (destination != "")) {
            val pooler : Pooler = Pooler()
            pooler.destinationAddress = destination
            pooler.endCity = endCity
            pooler.startCity = startCity
            pooler.isPooler = true
            myRef.child("Users").child(authenticator.currentUser!!.uid).setValue(pooler)
            val intent = Intent(this, MainPageActivity::class.java)
            startActivity(intent)
        } else {
            val toast = Toast.makeText(applicationContext, "een van de velden is niet correct of leeg", Toast.LENGTH_SHORT)
            toast.show()
        }

    }
}