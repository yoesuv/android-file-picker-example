package com.yoesuv.filepicker.menu.gallery.viewmodels

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoesuv.filepicker.data.COMPRESSOR_HEIGHT
import com.yoesuv.filepicker.data.COMPRESSOR_QUALITY
import com.yoesuv.filepicker.data.COMPRESSOR_WIDTH
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import kotlinx.coroutines.launch
import java.io.File

class GalleryViewModel: ViewModel() {

    var imagePath: MutableLiveData<String> = MutableLiveData()
    var imageFile: MutableLiveData<File> = MutableLiveData()

    fun setImageFile(context: Context, uri: Uri?) {
        if (uri != null) {
            // https://stackoverflow.com/a/22997547/3559183
            val pathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = context.contentResolver.query(uri, pathColumn, null, null, null)
            cursor?.moveToFirst()
            val columnIndex = cursor?.getColumnIndex(pathColumn[0]) ?: 0
            val strFilePath = cursor?.getString(columnIndex)
            if (strFilePath != null) {
                imagePath.postValue(strFilePath)
                // compress file image
                viewModelScope.launch {
                    val fileOriginal = File(strFilePath)
                    val fileCompressed = Compressor.compress(context, fileOriginal) {
                        default(width = COMPRESSOR_WIDTH, height = COMPRESSOR_HEIGHT, quality = COMPRESSOR_QUALITY)
                    }
                    imageFile.postValue(fileCompressed)
                }
            }
            cursor?.close()
        }
    }

}