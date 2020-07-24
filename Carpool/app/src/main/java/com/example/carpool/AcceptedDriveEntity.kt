package com.example.carpool

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "acceptedDrives")
data class AcceptedDriveEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private val id : Int,
    @ColumnInfo(name = "email") val email : String,
    @ColumnInfo(name = "requester_uid") val requesterUid : String,
    @ColumnInfo(name = "pooler_uid") val poolerUid : String,
    @ColumnInfo(name = "start_address" ) val startAddress : String,
    @ColumnInfo(name = "start_city" ) val startCity : String,
    @ColumnInfo(name = "end_city" ) val endCity : String,
    @ColumnInfo(name = "destination" ) val destination : String
)


