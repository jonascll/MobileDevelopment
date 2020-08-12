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
import kotlinx.android.synthetic.main.fragment_accepteddriverecyclerview.*

class AcceptedDriveRecyclerViewFragment : Fragment(){
    val currentUser = FirebaseAuth.getInstance().currentUser
    var acceptedDrives : ArrayList<AcceptedDrive> = ArrayList()
    val dbRef = FirebaseDatabase.getInstance().reference
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_accepteddriverecyclerview,container,false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllAcceptedDrivesForPooler {
            if(it) {
                acceptedDriveRecyclerViewFragment.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = AcceptedDriveRecyclerViewAdapter(acceptedDrives!!)
                }
            }
        }


    }



    fun getAllAcceptedDrivesForPooler(completion : (Boolean) -> Unit){

        dbRef.child("AcceptedDrives").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    val acceptedDrive = it.getValue(AcceptedDrive::class.java)
                    Log.d("debugging tag" , acceptedDrive?.poolerUid.toString())
                    if (acceptedDrive?.poolerUid == currentUser?.uid) {
                        if (acceptedDrive != null) {
                            acceptedDrives.add(acceptedDrive)
                        }
                    }
                }
                Log.d("debugging tag", acceptedDrives.toString())
                completion(true)
            }

            override fun onCancelled(error: DatabaseError) {
                completion(false)
            }

        })
    }

    fun getListOfGottenDrives() : ArrayList<AcceptedDrive> {
        return acceptedDrives
    }
    companion object {
        fun newInstance(): AcceptedDriveRecyclerViewFragment = AcceptedDriveRecyclerViewFragment()
    }
}