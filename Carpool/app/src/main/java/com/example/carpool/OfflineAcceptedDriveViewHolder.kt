package com.example.carpool

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OfflineAcceptedDriveViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.foundofflinedrivecard,parent, false)) {
    var startAddress : TextView? = null
    var endCity : TextView? = null
    var destination : TextView? = null
    var startCity : TextView? = null
    var email : TextView? = null
    var hiddenPoolerUid : TextView? = null

    init {
        startAddress = itemView.findViewById(R.id.startAddressOfflineDrive)
        endCity = itemView.findViewById(R.id.endCityOfflineDrive)
        destination = itemView.findViewById(R.id.destinationOfflineDrive)
        startCity = itemView.findViewById(R.id.startCityOfflineDrive)
        email = itemView.findViewById(R.id.emailOfflineDrive)
        hiddenPoolerUid = itemView.findViewById(R.id.hidden_pooler_uid)
    }


    fun bind(acceptedDrive : AcceptedDriveEntity) {
        startAddress?.text = acceptedDrive.startAddress
        startCity?.text = acceptedDrive.startCity
        endCity?.text = acceptedDrive.endCity
        destination?.text = acceptedDrive.destination
        email?.text = acceptedDrive.email
        hiddenPoolerUid?.text = acceptedDrive.poolerUid
    }


}