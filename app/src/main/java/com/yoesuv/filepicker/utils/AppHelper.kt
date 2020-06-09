package com.yoesuv.filepicker.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.yoesuv.filepicker.BuildConfig

fun logDebug(message: String) {
    if (BuildConfig.DEBUG) Log.d("result_debug", message)
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}