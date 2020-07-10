package com.example.carpool

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private val authenticator = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser = authenticator.currentUser
        if(currentUser != null) {
            val intent = Intent(this, CityPickerActivity::class.java)
            startActivity(intent)
        }
        setContentView(R.layout.activity_signup)


    }


    fun handleClickSignUpFragment(view: View) {
        val email = findViewById<EditText>(R.id.signUpEmailInput)
        val password = findViewById<EditText>(R.id.signUpPasswordInput)
        authenticator.createUserWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener(this) {task ->
            if(task.isSuccessful) {
                val intent = Intent(this, CityPickerActivity::class.java)
                startActivity(intent)
            } else {
                //TODO make a good error message if inputs arent filled or wrong
                Toast.makeText(baseContext, "Sign up failed.",
                    Toast.LENGTH_SHORT).show()
            }
        }

        }
    }
