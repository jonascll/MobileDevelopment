package com.example.carpool

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RequestViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.foundrequestcard, parent, false)) {
    var startAddress : TextView? = null
    var endCity : TextView? = null
    var destination : TextView? = null

    init {
        startAddress = itemView.findViewById(R.id.request_start_address)
        endCity = itemView.findViewById(R.id.request_end_city)
        destination = itemView.findViewById(R.id.request_destination)
    }

    fun bind (request : RequestedDrive) {
        startAddress?.text = request.startAddress
        endCity?.text = request.endCity
        destination?.text = request.destination
    }
}