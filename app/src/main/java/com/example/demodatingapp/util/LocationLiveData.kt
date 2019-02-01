package com.example.demodatingapp.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData

class LocationLiveData(val context: Context): LiveData<Location>(), LocationListener {

    private val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @SuppressLint("MissingPermission")
    @RequiresPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun refreshLocation() {
        val providers = locationManager.getProviders(true)
        val bestLocation: Location = providers.mapNotNull { provider ->
            locationManager.getLastKnownLocation(provider)
        }.minBy { location ->
            location.accuracy
        }!!

        onLocationChanged(bestLocation)
    }

    override fun onInactive() {
        super.onInactive()
        locationManager.removeUpdates(this)
    }

    override fun onLocationChanged(location: Location?) {
        postValue(location)
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    override fun onProviderEnabled(provider: String?) {}

    override fun onProviderDisabled(provider: String?) {}
}