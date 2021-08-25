package com.yoesuv.filepicker.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import pub.devrel.easypermissions.EasyPermissions

fun hasPermission(context: Context, permission: String): Boolean {
    return EasyPermissions.hasPermissions(context, permission)
}

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
    val uri = Uri.fromParts("package", context.packageName, null)
    intent.data = uri
    context.startActivity(intent)
}