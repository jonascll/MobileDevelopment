package com.example.carpool

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
@Dao
interface AcceptedDriveDao {
    @Query("SELECT * FROM acceptedDrives")
    fun getAll(): List<AcceptedDriveEntity>

    @Query("SELECT * FROM acceptedDrives WHERE pooler_uid IN (:poolerUids)")
    fun loadAllByPoolerUids(poolerUids: List<String>): List<AcceptedDriveEntity>

    @Query("SELECT * FROM acceptedDrives WHERE email Like (:email)")
    fun findByEmail(email: String): AcceptedDriveEntity

    @Delete
    fun delete(acceptedDriveEntity: AcceptedDriveEntity)

    @Insert
    fun insertNewAcceptedDrive(acceptedDriveEntity: AcceptedDriveEntity)


}