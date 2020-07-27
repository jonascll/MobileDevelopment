package com.example.carpool

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_seeofflineaccepteddrives.*
import kotlinx.android.synthetic.main.fragment_offlineacceptdrivesrecyclerview.*
import java.lang.Exception

class OfflineAcceptedDrivesRecyclerViewFragment : Fragment() {
    var acceptedDrives : List<AcceptedDriveEntity>? = null
    var deviceId : String? = null
    private var db = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Room.databaseBuilder(
            activity!!.applicationContext,
            AppDatabase::class.java, "acceptedDrives"
        ).build()
        return inflater.inflate(R.layout.fragment_offlineacceptdrivesrecyclerview,container,false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //TODO : dont forget to change pooler in firebase to make sure they have a device Id
        super.onViewCreated(view, savedInstanceState)
        getAcceptedDrivesFromLocalDatabase {
            if(it) {
                recyclerViewInFindOfflineDrives.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = OfflineAcceptedDriveViewAdapter(acceptedDrives!!)
                }
            }
        }

    }
    companion object {
        fun newInstance(): PoolerRecyclerViewFragment = PoolerRecyclerViewFragment()
    }


    fun getAcceptedDrivesFromLocalDatabase(completion : (Boolean) -> Unit) {
        val poolerUidField = view?.findViewById<TextView>(R.id.hidden_pooler_uid)
        val poolerUid = poolerUidField?.text

            val dbRefFirebase = FirebaseDatabase.getInstance().reference
            dbRefFirebase.child("Users").child(poolerUid.toString()).addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    val pooler : Pooler? = snapshot.getValue(Pooler::class.java)
                    deviceId = pooler?.deviceId
                    val db = Room.databaseBuilder(
                        activity!!.applicationContext,
                        AppDatabase::class.java, "acceptedDrivesDb"
                    ).build()
                    acceptedDrives = db.acceptedDriveDao().findByDeviceId(deviceId.toString()).value
                    completion(true)
                }

                override fun onCancelled(error: DatabaseError) {
                    //TODO good handling on error
                    completion(false)
                }

            })



    }
}