package com.example.carpool


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class PoolerRecyclerViewAdapter(val poolersFromSameCity: ArrayList<Pooler>?, val poolerUidList: ArrayList<String>?) : RecyclerView.Adapter<PoolerViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoolerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PoolerViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return poolersFromSameCity?.size ?: 0

    }

    override fun onBindViewHolder(holder: PoolerViewHolder, position: Int) {
        val pooler : Pooler = poolersFromSameCity?.get(position) ?: Pooler()
        val uid : String? = poolerUidList?.get(position)
        if (uid != null) {
            holder.bind(pooler, uid)
        }
    }


}