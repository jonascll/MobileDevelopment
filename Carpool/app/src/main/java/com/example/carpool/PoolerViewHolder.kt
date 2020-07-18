package com.example.carpool

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.foundpoolercard.view.*

class PoolerViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.foundpoolercard, parent, false)) {

    var startAddress : TextView? = null
    var endCity : TextView? = null
    var endAddress : TextView? = null
    var uid : TextView? = null

    init {
        startAddress = itemView.findViewById(R.id.person_startAddress)
        endCity = itemView.findViewById(R.id.person_endCity)
        endAddress = itemView.findViewById(R.id.person_endAddress)
        uid = itemView.findViewById(R.id.hidden_uid)
    }
    fun bind(pooler: Pooler, poolerUid: String) {
        startAddress?.text = String.format(itemView.resources.getString(R.string.find_pooler_pooler_start_city_text), pooler.startCity)
        endCity?.text = String.format(itemView.resources.getString(R.string.find_pooler_pooler_end_city_text), pooler.endCity)
        endAddress?.text = String.format(itemView.resources.getString(R.string.find_pooler_pooler_end_address_text), pooler.destinationAddress)
        uid?.text = poolerUid
    }

}