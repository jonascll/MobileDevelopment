package com.example.carpool

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView

class RequestViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.foundrequestcard, parent, false)) {
    var startAddress : TextView? = null
    var startCity : TextView? = null
    var endCity : TextView? = null
    var destination : TextView? = null
    var email : TextView? = null

    init {
        startAddress = itemView.findViewById(R.id.request_start_address)
        endCity = itemView.findViewById(R.id.request_end_city)
        destination = itemView.findViewById(R.id.request_destination)
        startCity = itemView.findViewById(R.id.request_start_city)
        email = itemView.findViewById(R.id.request_email)
    }

    fun bind (request : RequestedDrive) {
        startAddress?.text = request.startAddress
        endCity?.text = request.endCity
        destination?.text = request.destination
        startCity?.text = request.startCity
        email?.text = request.email
    }
}