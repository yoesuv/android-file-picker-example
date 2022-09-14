package com.yoesuv.filepicker.menu.location.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yoesuv.filepicker.utils.logDebug

class LocationViewModel: ViewModel() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var userLocation: MutableLiveData<String> = MutableLiveData()

    fun setup(context: Context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    fun getUserLocation() {
        userLocation.postValue("Processing")
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            val latLng = "${location.latitude},${location.longitude}"
            logDebug("LocationViewModel # location $latLng")
            userLocation.postValue(latLng)
        }
    }

}