package com.example.carpool

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(AcceptedDriveEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun acceptedDriveDao(): AcceptedDriveDao
}