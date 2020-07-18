package com.example.carpool

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class FindPoolerActivity : AppCompatActivity() {
    val currentUser = FirebaseAuth.getInstance().currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO make it so you cant see yourself by  hiding the pooler button and maybe showing another button that goes to a accept requests activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_findpooler)

    }

    fun handleClickOnFoundPooler(view : View) {
        //TODO find way to add the request of this user to the clicked pooler
        val uidField : TextView = view.findViewById(R.id.hidden_uid)
        val uidText = uidField.text
        Log.d("debugging tag", uidText.toString())
    }

}