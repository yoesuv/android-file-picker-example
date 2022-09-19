package com.yoesuv.filepicker.utils

import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import com.yoesuv.filepicker.BuildConfig
import es.dmoral.toasty.Toasty

fun logDebug(message: String) {
    if (BuildConfig.DEBUG) Log.d("result_debug", message)
}

fun logError(message: String?) {
    if (BuildConfig.DEBUG) Log.e("result_error", "$message")
}

fun Context.showToastSuccess(@StringRes message: Int) {
    Toasty.success(this, message).show()
}

fun Context.showToastError(@StringRes message: Int) {
    Toasty.error(this, message).show()
}