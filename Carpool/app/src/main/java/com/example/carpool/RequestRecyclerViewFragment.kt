package com.example.carpool

import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_requesteddriverecyclerview.*

class RequestRecyclerViewFragment : Fragment() {
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val listOfRequest = ArrayList<RequestedDrive>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_requesteddriverecyclerview,container,false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllReqeustByUid {
            if (it) {
                requested_drive_recyclerview.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = RequestRecyclerViewAdapter(listOfRequest)
                }
            }
        }
    }

    companion object {
        fun newInstance(): PoolerRecyclerViewFragment = PoolerRecyclerViewFragment()
    }

    private fun getAllReqeustByUid(completion : (Boolean) -> Unit) {
        val dbRef = FirebaseDatabase.getInstance().reference
        dbRef.child("RequestedDrives").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    val request : RequestedDrive? = it.getValue(RequestedDrive::class.java)

                        if (request != null) {
                            if(request.poolerUid == uid) {
                                listOfRequest.add(request)
                            }

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