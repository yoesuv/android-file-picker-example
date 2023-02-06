package com.yoesuv.filepicker.menu.location.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.utils.*

class LocationViewModel: ViewModel() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var userLocation: MutableLiveData<String> = MutableLiveData()

    fun setup(context: Context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    @SuppressLint("MissingPermission")
    fun getUserLocation(context: Context) {
        userLocation.postValue("Processing")
        if (forTest()) IdlingResource.increment()
        fusedLocationClient.lastLocation.addOnCompleteListener { task ->
            if (forTest()) {
                if(!IdlingResource.idlingresource.isIdleNow) {
                    IdlingResource.decrement()
                }
            }
            if (task.isSuccessful) {
                val location = task.result
                val latLng = "${location.latitude}, ${location.longitude}"
                logDebug("LocationViewModel # location $latLng")
                userLocation.postValue(latLng)
                context.showToastSuccess(R.string.toast_success_get_location)
            } else {
                logError("LocationViewModel # failed get location")
                context.showToastError(R.string.toast_failed_get_location)
            }
        }
    }

}