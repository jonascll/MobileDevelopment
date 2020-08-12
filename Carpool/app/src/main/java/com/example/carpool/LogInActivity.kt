package com.example.carpool

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import java.net.InetAddress
import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi


class LogInActivity : AppCompatActivity() {
    private val authenticator = FirebaseAuth.getInstance()


    override fun onBackPressed() {

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser = authenticator.currentUser
        setContentView(R.layout.activity_login)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ), 1
            )
        }
        if(currentUser != null) {
            val intent = Intent(this, CityPickerActivity::class.java)
            startActivity(intent)
        }
    }
    fun handleClickLogIn(view: View) {

        val email = findViewById<EditText>(R.id.EmailInput)
        val password = findViewById<EditText>(R.id.PasswordInput)


        authenticator.signInWithEmailAndPassword(
            email.text.toString(),
            password.text.toString()
        )
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val intent = Intent(this, CityPickerActivity::class.java)
                    startActivity(intent)

                } else {
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

    }
    fun handleClickSignUp(view: View) {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)

    }

    fun handleClickSeeOfflineRequests(view: View) {
        val intent = Intent(this, SeeOfflineAcceptedDrivesActivity::class.java)
        startActivity(intent)
    }
}