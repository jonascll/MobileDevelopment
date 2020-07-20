package com.example.carpool

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RequestRecyclerViewAdapter(val requestList : List<RequestedDrive>) : RecyclerView.Adapter<RequestViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RequestViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return requestList.size
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val request : RequestedDrive = requestList[position]
        holder.bind(request)
    }


}