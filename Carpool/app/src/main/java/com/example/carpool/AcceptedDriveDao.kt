package com.example.carpool

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

interface AcceptedDriveDao {
    @Query("SELECT * FROM accepteddriveentity")
    fun getAll(): List<AcceptedDriveEntity>

    @Query("SELECT * FROM accepteddriveentity WHERE pooler_uid IN (:poolerUids)")
    fun loadAllByPoolerUids(poolerUids: List<String>): List<String>

    @Query("SELECT * FROM accepteddriveentity WHERE email Like (:email)")
    fun findByEmail(email: String): String

    @Delete
    fun delete(acceptedDriveEntity: AcceptedDriveEntity)

    @Insert
    fun insertNewAcceptedDrive(acceptedDriveEntity: AcceptedDriveEntity)


}