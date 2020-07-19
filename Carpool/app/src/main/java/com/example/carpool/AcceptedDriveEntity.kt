package com.example.carpool

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AcceptedDriveEntity (
    @PrimaryKey(autoGenerate = true) val id : Int,
    @ColumnInfo(name = "uid") val uid : String,
    @ColumnInfo(name = "start_address" ) val startAddress : String,
    @ColumnInfo(name = "end_city" ) val endCity : String,
    @ColumnInfo(name = "destination" ) val destination : String
)


