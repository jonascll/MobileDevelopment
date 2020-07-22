package com.example.carpool

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ShowRequestedDrivesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO make it so you cant see yourself by  hiding the pooler button and maybe showing another button that goes to a accept requests activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_showrequesteddrives)
    }


    fun handleClickOnFoundRequest(view : View) {
        //TODO : handle click on card to show details
        //val intent = Intent(this,)
    }
}