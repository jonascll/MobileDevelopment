package com.example.carpool

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class LogInFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login,container,false)
    }


    fun getLogInButton(): Button? {
        return view?.findViewById(R.id.LogInButton)
    }
    fun getSignUpButton(): Button? {
        return view?.findViewById(R.id.SignUpButton)
    }
}