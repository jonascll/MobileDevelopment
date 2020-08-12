package com.example.carpool

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class FindPoolerFormActivity : AppCompatActivity() {
        var pickedCityId = 0L
        private val authenticator = FirebaseAuth.getInstance()
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val currentUser = authenticator.currentUser
            setContentView(R.layout.activity_findpoolerform)
        }
    fun setSpinnerValue() {
        pickedCityId = intent.getLongExtra("pickedCityId", 0)
        val fragment = supportFragmentManager.findFragmentById(R.id.cityPickerFindPoolerFragment) as CityPickerFragment
        val spinner = fragment.getSpinner()
        Log.d("debugging tag", spinner.toString())
        spinner?.setSelection(pickedCityId.toInt())
    }

    fun handleClickFindPoolerFormButton(view: View) {
        val intent = Intent(this, FindPoolerActivity::class.java)
        var fragment = supportFragmentManager.findFragmentById(R.id.findPoolerCityPickerEndCity) as CityPickerFragment
        var spinner = fragment.getSpinner()
        val endAddressfield = findViewById<EditText>(R.id.findPoolerEndAdres)
        val endAddress = endAddressfield.text.toString()
        val startAddressField = findViewById<TextView>(R.id.findPoolerStartAdres)
        val startAddress = startAddressField.text.toString()
        if((endAddress != "") && (startAddress != "")) {
            val toast = Toast.makeText(applicationContext, "velden zijn leeg of niet correct", Toast.LENGTH_SHORT)
            toast.show()
        } else {
            intent.putExtra("endCity", spinner?.selectedItem.toString())
            intent.putExtra("endAddress", endAddress)
            intent.putExtra("startAddress",startAddress)
            fragment = supportFragmentManager.findFragmentById(R.id.cityPickerFindPoolerFragment) as CityPickerFragment
            spinner = fragment.getSpinner()
            intent.putExtra("startCity", spinner?.selectedItem.toString())
            startActivity(intent)
        }

        }
}