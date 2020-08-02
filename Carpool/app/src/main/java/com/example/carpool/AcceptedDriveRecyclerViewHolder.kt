package com.example.carpool

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AcceptedDriveRecyclerViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.foundonlinedrivecard,parent, false)) {
    var startAddress : TextView? = null
    var endCity : TextView? = null
    var destination : TextView? = null
    var startCity : TextView? = null
    var email : TextView? = null
    var hiddenPoolerUid : TextView? = null
    var requeserUid : TextView? = null
    init {
        startAddress = itemView.findViewById(R.id.startAddressOnlineDrive)
        endCity = itemView.findViewById(R.id.endCityOnlineDrive)
        destination = itemView.findViewById(R.id.destinationOnlineDrive)
        startCity = itemView.findViewById(R.id.startCityOnlineDrive)
        email = itemView.findViewById(R.id.emailOnlineDrive)
        hiddenPoolerUid = itemView.findViewById(R.id.hidden_pooler_uid_online)
        requeserUid = itemView.findViewById(R.id.hidden_requester_uid_online)
    }


    fun bind(acceptedDrive : AcceptedDrive) {
        Log.d("inbinder", acceptedDrive.toString())
        startAddress?.text = acceptedDrive.startAddress
        startCity?.text = acceptedDrive.startCity
        endCity?.text = acceptedDrive.endCity
        destination?.text = acceptedDrive.destination
        email?.text = acceptedDrive.email
        hiddenPoolerUid?.text = acceptedDrive.poolerUid
        requeserUid?.text = acceptedDrive.requesterUid
    }
}