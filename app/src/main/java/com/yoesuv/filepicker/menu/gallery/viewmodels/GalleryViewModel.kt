package com.yoesuv.filepicker.menu.gallery.viewmodels

import android.Manifest
import android.app.Activity
import androidx.lifecycle.ViewModel
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.data.RC_READ_EXTERNAL_STORAGE
import com.yoesuv.filepicker.utils.hasPermission
import com.yoesuv.filepicker.utils.logDebug
import pub.devrel.easypermissions.EasyPermissions

class GalleryViewModel : ViewModel() {

    fun clickOpenGallery(activity: Activity) {
        if (hasPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            openGallery()
        } else {
            val rationale = activity.getString(R.string.rationale_read_storage_gallery)
            EasyPermissions.requestPermissions(
                activity,
                rationale,
                RC_READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    fun openGallery() {
        logDebug("GalleryViewModel # open gallery")
    }

}