package com.example.carpool


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class RecyclerViewAdapter(val city: String?) : RecyclerView.Adapter<RecyclerViewAdapter.PoolerViewHolder>() {
//TODO fix white screen (used resource to figure out recycler view https://code.tutsplus.com/tutorials/getting-started-with-recyclerview-and-cardview-on-android--cms-23465)
    class PoolerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardView: CardView = itemView.findViewById(R.id.cv)
        val startCity: TextView =  itemView.findViewById(R.id.person_email)
        val endAddress : TextView = itemView.findViewById(R.id.person_endAddress)
    }
    val poolersFromSameCity : ArrayList<Pooler> = getAllPoolersFromSameCity()

    private fun getAllPoolersFromSameCity(): ArrayList<Pooler> {
        val dbRef = FirebaseDatabase.getInstance().reference
        val poolerFromSameCity = ArrayList<Pooler>()
        dbRef.child("Users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    val pooler : Pooler? = it.getValue(Pooler::class.java)
                    //TODO also pass startCity to make sure their starting city is also the same
                    if(pooler?.isPooler!! && pooler.endCity == city){
                        poolerFromSameCity.add(pooler)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return poolerFromSameCity
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoolerViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_foundpoolercard, parent, false)
        return PoolerViewHolder(v)
    }

    override fun getItemCount(): Int {
        return poolersFromSameCity.size
    }

    override fun onBindViewHolder(holder: PoolerViewHolder, position: Int) {
        holder.endAddress.setText(poolersFromSameCity.get(position).destinationAddress)
        holder.startCity.setText(poolersFromSameCity.get(position).startCity)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }





}