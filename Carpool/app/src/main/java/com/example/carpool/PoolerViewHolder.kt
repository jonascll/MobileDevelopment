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

    init {
        startAddress = itemView.findViewById(R.id.person_startAddress)
        endCity = itemView.findViewById(R.id.person_endAddress)
    }
    fun bind(pooler: Pooler) {
        startAddress?.text = pooler.startAddress
        endCity?.text = pooler.endCity
    }

}