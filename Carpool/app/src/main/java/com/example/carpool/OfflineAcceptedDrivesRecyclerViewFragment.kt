package com.example.carpool

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_offlineacceptdrivesrecyclerview.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class OfflineAcceptedDrivesRecyclerViewFragment() : Fragment() {
    val executorService : ExecutorService = Executors.newFixedThreadPool(1)


    var acceptedDrives : ArrayList<AcceptedDriveEntity> = ArrayList()
    var deviceId : String? = null
    var db : AppDatabase? = null
    val futures :  ArrayList<Future<*>>  = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //TODO remove most of your !! calls because this is not the best thing to do in most cases
        db = Room.databaseBuilder(context!!.applicationContext, AppDatabase::class.java, "acceptedDrivesDb").fallbackToDestructiveMigration().build()
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
                    adapter = OfflineAcceptedDriveViewAdapter(acceptedDrives)
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
                    getLocalDatabaseDrives()
                    completion(true)
                }

                override fun onCancelled(error: DatabaseError) {
                    //TODO good handling on error
                    completion(false)
                }

            })



    }

    fun getLocalDatabaseDrives() {
        //TODO : fix future being null dont know the cause
        val runnable = Runnable { acceptedDrives = db?.acceptedDriveDao()?.findByDeviceId(deviceId.toString()) as ArrayList<AcceptedDriveEntity> }
        val futureGotten : Future<*>? = executorService.submit(runnable)
        if (futureGotten != null) {
            futures.add(futureGotten)
        }
        for (future : Future<*> in futures) {
            val gottenDrive = future.get()
            if(gottenDrive != null) {
                acceptedDrives.add(gottenDrive as AcceptedDriveEntity)
            }


        }
    }
}