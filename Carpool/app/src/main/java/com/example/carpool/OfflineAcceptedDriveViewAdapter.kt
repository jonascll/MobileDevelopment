package com.example.carpool

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class OfflineAcceptedDriveViewAdapter (private val acceptedDrives : List<AcceptedDriveEntity>) : RecyclerView.Adapter<OfflineAcceptedDriveViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OfflineAcceptedDriveViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return OfflineAcceptedDriveViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return acceptedDrives.size
    }

    override fun onBindViewHolder(holder: OfflineAcceptedDriveViewHolder, position: Int) {
        val acceptedDrive : AcceptedDriveEntity = acceptedDrives[position]
        holder.bind(acceptedDrive)
    }

}