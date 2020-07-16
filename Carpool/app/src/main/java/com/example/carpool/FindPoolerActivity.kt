package com.example.carpool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FindPoolerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO make it so you cant see yourself by  hiding the pooler button and maybe showing another button that goes to a accept requests activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_findpooler)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewInFindPooler)
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        val recyclerViewAdapter : RecyclerViewAdapter = RecyclerViewAdapter(intent.getStringExtra("endCity"))
        recyclerView.adapter = recyclerViewAdapter
    }

}