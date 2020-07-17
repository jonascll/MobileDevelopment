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
import kotlinx.android.synthetic.main.fragment_recyclerview.*

class RecyclerViewFragment : Fragment() {
    val auth = FirebaseAuth.getInstance()
    val uid = auth.currentUser?.uid
    var endCity : String? = null
    val poolerFromSameCity = ArrayList<Pooler>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        endCity = activity?.intent?.getStringExtra("endCity")
        Log.d("debugging tag", "hello mom im in the recycler fragment")
        Log.d("debugging tag", "uid $uid endCity $endCity")
        return inflater.inflate(R.layout.fragment_recyclerview,container,false)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllPoolersFromSameCity()
        Log.d("debugging tag created", poolerFromSameCity.toString())
        recyclerViewInFindPooler.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = RecyclerViewAdapter(poolerFromSameCity)
        }
    }
    companion object {
        fun newInstance(): RecyclerViewFragment = RecyclerViewFragment()
    }
    private fun getAllPoolersFromSameCity(){
        //TODO fix empty array issue here its not empty in oncreate it is
        val dbRef = FirebaseDatabase.getInstance().reference

        dbRef.child("Users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("debugging tag", snapshot.toString())
                snapshot.children.forEach{
                    val pooler : Pooler? = it.getValue(Pooler::class.java)
                    Log.d("debugging tag", pooler!!.endCity)

                    //TODO also pass startCity to make sure their starting city is also the same
                    if(pooler.isPooler && pooler.endCity == endCity){
                        Log.d("debugging tag", pooler.toString())
                        poolerFromSameCity.add(pooler)
                        Log.d("debugging tag", poolerFromSameCity.toString())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}