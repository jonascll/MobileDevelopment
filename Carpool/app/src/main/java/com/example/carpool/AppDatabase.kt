package com.example.carpool

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(AcceptedDriveEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    val DB_NAME : String = "acceptedDrivesDb"
    var instanceOfDB : AppDatabase? = null


    abstract fun acceptedDriveDao(): AcceptedDriveDao

}