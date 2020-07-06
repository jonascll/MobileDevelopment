package com.example.carpool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private val authenticator = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
    fun handleClick(view: View) {
        val fragment = LogInFragment()

        val email = findViewById<EditText>(R.id.UsernameInput)
        val password = findViewById<EditText>(R.id.PasswordInput)


            authenticator.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(baseContext, "Authentication success.",
                            Toast.LENGTH_SHORT).show()

                    } else {
                        // If sign in fails, display a message to the user.

                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }





                }

    }
}