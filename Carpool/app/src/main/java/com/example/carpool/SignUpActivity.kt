package com.example.carpool

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    private val authenticator = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }

    fun handleClickSignUpFragment(view: View) {
        val email = findViewById<EditText>(R.id.signUpEmailInput)
        val password = findViewById<EditText>(R.id.signUpPasswordInput)
        authenticator.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
        }
    }
