package com.yoesuv.filepicker.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import com.yoesuv.filepicker.BuildConfig
import es.dmoral.toasty.Toasty

fun logDebug(message: String) {
    if (BuildConfig.DEBUG) Log.d("result_debug", message)
}

fun logError(message: String?) {
    if (BuildConfig.DEBUG) Log.e("result_error", "$message")
}

fun Activity.showToastSuccess(@StringRes message: Int) {
    Toasty.success(this, message).show()
}

fun Activity.showToastError(@StringRes message: Int) {
    Toasty.error(this, message).show()
}

fun isTiramisu(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
}

fun isPieOrLower(): Boolean {
    return Build.VERSION.SDK_INT <= Build.VERSION_CODES.P
}

fun forTest(): Boolean {
    return BuildConfig.FLAVOR.equals("forTest", true)
}