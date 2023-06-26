package com.yoesuv.filepicker.data

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

// kind permission
const val PERM_CAMERA = Manifest.permission.CAMERA
const val PERM_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
const val PERM_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
const val PERM_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
const val PERM_READ_MEDIA_IMAGES = Manifest.permission.READ_MEDIA_IMAGES

// req code permission
const val RC_READ_EXTERNAL_STORAGE = 23
const val RC_FINE_LOCATION = 24
const val RC_WRITE_EXTERNAL_STORAGE = 26

const val BASE_URL = "https://raw.githubusercontent.com"
const val DOWNLOAD_LINK = "yoesuv/GetX-Playground/master/Sample%20Document%20pdf.pdf"

const val COMPRESSOR_QUALITY = 90
const val COMPRESSOR_WIDTH = 1224
const val COMPRESSOR_HEIGHT = 1632