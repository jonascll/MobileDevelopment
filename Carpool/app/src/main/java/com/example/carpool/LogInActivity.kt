package com.example.carpool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {
    private val authenticator = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val currentUser = authenticator.currentUser
        Log.d("debugging tag" , currentUser?.displayName.toString())
        setContentView(R.layout.activity_login)

    }
    fun handleClickLogIn(view: View) {

        val email = findViewById<EditText>(R.id.EmailInput)
        val password = findViewById<EditText>(R.id.PasswordInput)


            authenticator.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val intent = Intent(this, CityPickerActivity::class.java)
                        startActivity(intent)

                    } else {
                        // If sign in fails, display a message to the user.
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
}