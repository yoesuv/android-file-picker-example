package com.yoesuv.filepicker.utils

import android.app.Activity
import android.content.Intent

class FileHelper {

    companion object {
        fun chooseFile(activity: Activity, requestCode: Int) {
            val intent: Intent?
            val intentChoose = Intent(Intent.ACTION_GET_CONTENT)
            intentChoose.addCategory(Intent.CATEGORY_OPENABLE)
            intentChoose.type = "*/*"

            intent = Intent.createChooser(intentChoose, "Select Document")
            activity.startActivityForResult(intent, requestCode)
        }
    }

}