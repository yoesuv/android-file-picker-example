package com.yoesuv.filepicker.menu.gallery.viewmodels

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel: ViewModel() {

    var imagePath: MutableLiveData<String> = MutableLiveData()

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
            }
            cursor?.close()
        }
    }

}