package com.yoesuv.filepicker.menu.location.viewmodels

import android.annotation.SuppressLint
import android.app.Activity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.utils.*

class LocationViewModel: ViewModel() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var userLocation: MutableLiveData<String> = MutableLiveData()

    fun setup(activity: Activity) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    }

    @SuppressLint("MissingPermission")
    fun getUserLocation(activity: Activity) {
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
                if (location != null) {
                    val latLng = "${location.latitude}, ${location.longitude}"
                    logDebug("LocationViewModel # location $latLng")
                    userLocation.postValue(latLng)
                    activity.showSnackbarSucces(R.string.toast_success_get_location)
                } else {
                    userLocation.postValue("")
                    activity.showSnackbarError(R.string.toast_failed_get_location)
                }
            } else {
                logError("LocationViewModel # failed get location")
                activity.showSnackbarError(R.string.toast_failed_get_location)
            }
        }
    }

}