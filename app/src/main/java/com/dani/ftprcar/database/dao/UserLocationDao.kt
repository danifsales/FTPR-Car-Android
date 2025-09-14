package com.dani.ftprcar.database.dao

import androidx.room.Insert
import androidx.room.Query

interface UserLocationDao {
    @Insert
    suspend fun insert(userLocation: UserLocation)

    @Query("SELECT * FROM user_location_table")
    suspend fun getAllUserLocation(): List<UserLocation>?

    @Query("SELECT * FROM user_location_table ORDER BY id DESC LIMIT 1")
    suspend fun getLastLocation(): UserLocation?
}