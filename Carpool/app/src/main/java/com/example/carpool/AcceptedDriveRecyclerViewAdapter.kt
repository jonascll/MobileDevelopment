package com.example.carpool

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class AcceptedDriveRecyclerViewAdapter(private val acceptedDrives : List<AcceptedDriveEntity>) : RecyclerView.Adapter<AcceptedDriveRecyclerViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AcceptedDriveRecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AcceptedDriveRecyclerViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return acceptedDrives.size
    }

    override fun onBindViewHolder(holder: AcceptedDriveRecyclerViewHolder, position: Int) {
        val acceptedDrive : AcceptedDriveEntity = acceptedDrives[position]
        holder.bind(acceptedDrive)
    }


}