package com.yoesuv.filepicker.menu.camera.viewmodels

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.data.COMPRESSOR_HEIGHT
import com.yoesuv.filepicker.data.COMPRESSOR_QUALITY
import com.yoesuv.filepicker.data.COMPRESSOR_WIDTH
import com.yoesuv.filepicker.utils.logDebug
import com.yoesuv.filepicker.utils.showToastError
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import kotlinx.coroutines.launch
import java.io.File

class CameraViewModel: ViewModel() {

    var imageFile: MutableLiveData<File?> = MutableLiveData()
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

    fun setPhotoUri(context: Activity, uri: Uri?) {
        viewModelScope.launch {
            if (photoFile != null) {
                val fileCompressed = Compressor.compress(context, photoFile!!) {
                    default(width = COMPRESSOR_WIDTH, height = COMPRESSOR_HEIGHT, quality = COMPRESSOR_QUALITY)
                }
                logDebug("CameraViewModel # file compressed path : ${fileCompressed.absolutePath}")
                imageFile.postValue(fileCompressed)
                // delete image from camera
                uri?.let {
                    context.contentResolver.delete(it, null, null)
                }
            } else {
                context.showToastError(R.string.toast_failed_get_image_camera)
            }
        }
    }

}