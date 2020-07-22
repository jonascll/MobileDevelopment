package com.example.carpool

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class LogOutButtonFragment : Fragment() {
    val auth = FirebaseAuth.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_logoutbutton,container,false)
        val button : ImageView? = view?.findViewById(R.id.LogOutButton)
        button?.setOnClickListener(View.OnClickListener {
            auth.signOut()
            val intent = Intent(this.context, LogInActivity::class.java)
            startActivity(intent)
        })
        return view
    }


}