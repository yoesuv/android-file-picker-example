package com.yoesuv.filepicker.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener

fun checkAppPermission(context: Context, permission: String, onGranted:() -> Unit, onDenied:() -> Unit) {
    Dexter.withContext(context)
        .withPermission(permission)
        .withListener(object: PermissionListener {
            override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                onGranted()
            }
            override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                if (response?.isPermanentlyDenied!!) {
                    openAppSettings(context)
                } else {
                    onDenied()
                }
            }
            override fun onPermissionRationaleShouldBeShown(request: PermissionRequest?, token: PermissionToken?) {
                token?.continuePermissionRequest()
            }
        })
        .check()
}

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", context.packageName, null)
    intent.data = uri
    context.startActivity(intent)
}