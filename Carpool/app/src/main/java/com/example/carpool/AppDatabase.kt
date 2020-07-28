package com.example.carpool

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(AcceptedDriveEntity::class), version = 3)
abstract class AppDatabase : RoomDatabase() {
    val DB_NAME : String = "acceptedDrivesDb"
    abstract fun acceptedDriveDao(): AcceptedDriveDao

}