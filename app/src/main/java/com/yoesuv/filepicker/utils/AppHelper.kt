package com.yoesuv.filepicker.utils

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.yoesuv.filepicker.BuildConfig
import com.yoesuv.filepicker.R

fun logDebug(message: String) {
    if (BuildConfig.DEBUG) Log.d("result_debug", message)
}

fun logError(message: String?) {
    if (BuildConfig.DEBUG) Log.e("result_error", "$message")
}

fun Activity.showSnackbarSucces(@StringRes message: Int) {
    val rootView = this.window.decorView.rootView
    val snack = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)
    val green = ContextCompat.getColor(this, R.color.green500)
    snack.view.setBackgroundColor(green)
    val snackTextView: TextView = snack.view.findViewById(com.google.android.material.R.id.snackbar_text)
    snackTextView.setTextColor(Color.WHITE)
    snack.show()
}

fun Activity.showSnackbarError(@StringRes message: Int) {
    val rootView = this.window.decorView.rootView
    val snack = Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT)
    val red = ContextCompat.getColor(this, R.color.red500)
    snack.view.setBackgroundColor(red)
    val snackTextView: TextView = snack.view.findViewById(com.google.android.material.R.id.snackbar_text)
    snackTextView.setTextColor(Color.WHITE)
    snack.show()
}

fun isTiramisu(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
}

fun isUpsideDown(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE
}

fun isPieOrLower(): Boolean {
    return Build.VERSION.SDK_INT <= Build.VERSION_CODES.P
}

fun isOreo(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
}

fun forTest(): Boolean {
    return BuildConfig.FLAVOR.equals("forTest", true)
}