package com.yoesuv.filepicker.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import com.yoesuv.filepicker.BuildConfig

fun logDebug(message: String) {
    if (BuildConfig.DEBUG) Log.d("result_debug", message)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}