package com.example.carpool


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class RecyclerViewAdapter(val poolersFromSameCity: ArrayList<Pooler>?, val poolerUidList: ArrayList<String>?) : RecyclerView.Adapter<PoolerViewHolder>() {
//TODO fix white screen (used resource to figure out recycler view https://code.tutsplus.com/tutorials/getting-started-with-recyclerview-and-cardview-on-android--cms-23465)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoolerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PoolerViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return poolersFromSameCity!!.size
    }

    override fun onBindViewHolder(holder: PoolerViewHolder, position: Int) {
        val pooler : Pooler = poolersFromSameCity?.get(position) ?: Pooler()
        val uid : String? = poolerUidList?.get(position)
        if (uid != null) {
            holder.bind(pooler, uid)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }





}