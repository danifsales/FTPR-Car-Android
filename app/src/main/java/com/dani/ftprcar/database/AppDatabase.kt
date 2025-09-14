package com.dani.ftprcar.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dani.ftprcar.database.model.UserLocation
import com.dani.ftprcar.database.dao.UserLocationDao
import com.dani.ftprcar.database.converter.DateConverters


@Database(entities = [UserLocation::class], version = 1)
@TypeConverters(DateConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userLocationDao(): UserLocationDao
}