package com.example.carpool

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
@Dao
interface AcceptedDriveDao {
    @Query("SELECT * FROM acceptedDrives")
    fun getAll(): LiveData<List<AcceptedDriveEntity>>

    @Query("SELECT * FROM acceptedDrives WHERE pooler_uid IN (:poolerUids)")
    fun loadAllByPoolerUids(poolerUids: List<String>): LiveData<List<AcceptedDriveEntity>>

    @Query("DELETE FROM acceptedDrives WHERE requester_uid = :requesterUid AND pooler_uid = :poolerUid")
    fun deleteWithRequestUidAndPoolerUid(requesterUid: String, poolerUid : String)

    @Insert
    fun insertNewAcceptedDrive(acceptedDriveEntity: AcceptedDriveEntity)


}