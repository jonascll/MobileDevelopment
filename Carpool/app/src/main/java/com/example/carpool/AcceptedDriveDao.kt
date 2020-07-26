package com.example.carpool

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
@Dao
interface AcceptedDriveDao {
    @Query("SELECT * FROM acceptedDrives")
    fun getAll(): ArrayList<AcceptedDriveEntity>

    @Query("SELECT * FROM acceptedDrives WHERE pooler_uid IN (:poolerUids)")
    fun loadAllByPoolerUids(poolerUids: List<String>): ArrayList<AcceptedDriveEntity>

    @Query("SELECT * FROM acceptedDrives WHERE device_id Like (:deviceId)")
    fun findByDeviceId(deviceId: String): ArrayList<AcceptedDriveEntity>

    @Delete
    fun delete(acceptedDriveEntity: AcceptedDriveEntity)

    @Insert
    fun insertNewAcceptedDrive(acceptedDriveEntity: AcceptedDriveEntity)


}