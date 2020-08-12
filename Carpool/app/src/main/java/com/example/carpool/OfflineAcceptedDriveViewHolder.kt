package com.example.carpool

import android.util.Log
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
        Log.d("inbinder", acceptedDrive.toString())
        startAddress?.text = String.format(itemView.resources.getString(R.string.start_address_requested_drive_detail),acceptedDrive.startAddress)
        startCity?.text = String.format(itemView.resources.getString(R.string.start_city_requested_drive_detail),acceptedDrive.startCity)
        endCity?.text = String.format(itemView.resources.getString(R.string.end_city_requested_drive_detail),acceptedDrive.endCity)
        destination?.text = String.format(itemView.resources.getString(R.string.end_address_requested_drive_detail),acceptedDrive.destination)
        email?.text = String.format(itemView.resources.getString(R.string.email_requested_drive_detail),acceptedDrive.email)
        hiddenPoolerUid?.text = String.format(itemView.resources.getString(R.string.pooler_uid_request_drive_detail),acceptedDrive.poolerUid)
    }


}