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
import kotlinx.android.synthetic.main.fragment_accepteddriverecyclerview.*

class AcceptedDriveRecyclerViewFragment : Fragment(){
    val currentUser = FirebaseAuth.getInstance().currentUser
    var acceptedDrives : ArrayList<AcceptedDriveEntity>? = null
    val dbRef = FirebaseDatabase.getInstance().reference
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_accepteddriverecyclerview,container,false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //TODO : dont forget to change pooler in firebase to make sure they have a device Id
        super.onViewCreated(view, savedInstanceState)
        getAllAcceptedDrivesForPooler {
            if(it) {
                acceptedDriveRecyclerViewFragment.apply {
                    layoutManager = LinearLayoutManager(activity)
                    if(acceptedDrives != null) {
                        adapter = OfflineAcceptedDriveViewAdapter(acceptedDrives!!)
                    }

                }
            } else {
                // TODO implement good code here
            }


        }


    }



    fun getAllAcceptedDrivesForPooler(completion : (Boolean) -> Unit){

        dbRef.child("AcceptedDrives").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach{
                    val acceptedDrive = it.getValue(AcceptedDriveEntity::class.java)
                    if (acceptedDrive?.poolerUid == currentUser?.uid) {
                        if (acceptedDrive != null) {
                            acceptedDrives?.add(acceptedDrive)
                        }
                    }
                }
                completion(true)
            }

            override fun onCancelled(error: DatabaseError) {
                //TODO implement good code here
                completion(false)
            }

        })
    }
}