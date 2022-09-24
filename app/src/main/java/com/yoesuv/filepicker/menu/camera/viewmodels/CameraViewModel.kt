package com.yoesuv.filepicker.menu.camera.viewmodels

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yoesuv.filepicker.utils.logDebug
import java.io.File

class CameraViewModel: ViewModel() {

    var imagePath: MutableLiveData<String> = MutableLiveData()
    private var photoFile: File? = null

    fun uriCamera(context: Context): Uri? {
        val packageName = context.packageName
        val dirs = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        photoFile = File.createTempFile("IMG_", ".jpg", dirs)
        if (photoFile != null) {
            return FileProvider.getUriForFile(context, "$packageName.provider", photoFile!!)
        } else {
            return null
        }
    }

    fun setPhotoUri(context: Context, uri: Uri?) {
        logDebug("CameraViewModel # photo uri $uri")
        logDebug("CameraViewModel # photo exists : ${photoFile?.exists()}")
        logDebug("CameraViewModel # photo path : ${photoFile?.absolutePath}")
        imagePath.postValue(photoFile?.absolutePath)
    }

}