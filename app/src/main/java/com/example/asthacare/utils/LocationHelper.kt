package com.example.asthacare.utils

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices

class LocationHelper(context: Context) {

    private val client =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun getLocation(callback: (Double, Double) -> Unit) {
        client.lastLocation.addOnSuccessListener { loc ->
            if (loc != null) {
                callback(loc.latitude, loc.longitude)
            }
        }
    }
}