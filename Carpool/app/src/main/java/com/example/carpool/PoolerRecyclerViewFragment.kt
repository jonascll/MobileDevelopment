package com.example.carpool

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_poolerrecyclerview.*

class PoolerRecyclerViewFragment : Fragment() {
    val auth = FirebaseAuth.getInstance()
    var endCity : String? = null
    var startCity : String? = null
    val poolerFromSameCity = ArrayList<Pooler>()
    val poolerFoundUid = ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        endCity = activity?.intent?.getStringExtra("endCity")
        startCity = activity?.intent?.getStringExtra("startCity")
        return inflater.inflate(R.layout.fragment_poolerrecyclerview,container,false)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllPoolersFromSameCity{
            if (it) {
                if(recyclerViewInFindPooler != null) {
                    recyclerViewInFindPooler.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = PoolerRecyclerViewAdapter(poolerFromSameCity, poolerFoundUid)
                    }
                }
            }


        }

    }
    companion object {
        fun newInstance(): PoolerRecyclerViewFragment = PoolerRecyclerViewFragment()
    }
    private fun getAllPoolersFromSameCity(completion : (Boolean) -> Unit) {
        val dbRef = FirebaseDatabase.getInstance().reference
        dbRef.child("Users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    val pooler : Pooler? = it.getValue(Pooler::class.java)
                    if(pooler!!.isPooler && pooler.endCity == endCity && pooler.startCity == startCity){
                        poolerFromSameCity.add(pooler)
                        poolerFoundUid.add(it.key.toString())
                    }
                }
                completion(true)
            }

            override fun onCancelled(error: DatabaseError) {
                completion(false)
            }
        })

    }
}