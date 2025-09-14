package com.dani.ftprcar.location
import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.tasks.await

object LocationHelper {
    @SuppressLint("MissingPermission")
    suspend fun last(ctx: Context): Pair<Double,Double>? {
        val loc = LocationServices.getFusedLocationProviderClient(ctx).lastLocation.await() ?: return null
        return loc.latitude to loc.longitude
    }
}
