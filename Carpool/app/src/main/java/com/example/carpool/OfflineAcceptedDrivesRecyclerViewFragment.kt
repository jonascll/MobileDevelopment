package com.example.carpool

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_offlineacceptdrivesrecyclerview.*
import java.lang.Exception
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class OfflineAcceptedDrivesRecyclerViewFragment() : Fragment() {



    var acceptedDrives : ArrayList<AcceptedDriveEntity> = ArrayList()
    var db : AppDatabase? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = Room.databaseBuilder(context!!.applicationContext, AppDatabase::class.java, "acceptedDrivesDb").fallbackToDestructiveMigration().build()
        return inflater.inflate(R.layout.fragment_offlineacceptdrivesrecyclerview,container,false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        getAllLocalAcceptedDrives {
            if(it) {
                if(recyclerViewInFindOfflineDrives != null) {
                    recyclerViewInFindOfflineDrives.apply {
                        layoutManager = LinearLayoutManager(activity)
                        adapter = OfflineAcceptedDriveViewAdapter(acceptedDrives)
                    }
                }
            }
        }

        }


    private fun getAllLocalAcceptedDrives(completion : (Boolean) -> Unit) {
        try {
            val observer : Observer<List<AcceptedDriveEntity>> = Observer {
                if(it != null) {
                    acceptedDrives = it as ArrayList<AcceptedDriveEntity>
                    completion(true)
                } else {
                    completion(false)
                }

            }
            db?.acceptedDriveDao()?.getAll()?.observe(this, observer)


        } catch (e : Exception) {
            completion(false)
        }

    }

    companion object {
        fun newInstance(): OfflineAcceptedDrivesRecyclerViewFragment = OfflineAcceptedDrivesRecyclerViewFragment()
    }

}