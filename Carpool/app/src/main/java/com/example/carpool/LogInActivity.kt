package com.example.carpool

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import java.net.InetAddress


class LogInActivity : AppCompatActivity() {
    private val authenticator = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO : redirect if already logged in
        super.onCreate(savedInstanceState)
        val currentUser = authenticator.currentUser
        setContentView(R.layout.activity_login)


    }
    fun handleClickLogIn(view: View) {

        val email = findViewById<EditText>(R.id.EmailInput)
        val password = findViewById<EditText>(R.id.PasswordInput)


            authenticator.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        val intent = Intent(this, CityPickerActivity::class.java)
                        startActivity(intent)

                    } else {
                        //TODO make a good error message if inputs arent filled or wrong
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }

                }

    }
    fun handleClickSignUp(view: View) {
        val intent = Intent(this,SignUpActivity::class.java)
        startActivity(intent)

    }

    fun handleClickSeeOfflineRequests(view: View) {
        val intent = Intent(this, SeeOfflineAcceptedDrivesActivity::class.java)
        startActivity(intent)
    }
    override fun onBackPressed() {

    }


}