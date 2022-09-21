package com.yoesuv.filepicker.menu.file.viewmodels

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.utils.MyFileUtils
import com.yoesuv.filepicker.utils.logDebug

class FileViewModel: ViewModel() {

    var filePath: ObservableField<String> = ObservableField()
    var fileName: ObservableField<String> = ObservableField()
    var fileSize: ObservableField<String> = ObservableField()

    fun setSelectedFile(context: Context, uri: Uri) {
        logDebug("FileViewModel # setSelectedFile ${uri.path}")
        try {
            /*val pathColumn = arrayOf(MediaStore.Files.FileColumns.DATA)
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor?.moveToFirst()
            //val columnIndex = cursor?.getColumnIndex(pathColumn[0]) ?: 0
            val columnIndex = cursor?.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
            val columnSize = cursor?.getColumnIndexOrThrow(OpenableColumns.SIZE) ?: 0
            if (columnIndex != null) {
                val strFilePath = cursor.getString(columnIndex)
                val strFileSize = cursor.getString(columnSize)
                //val strFilePath = cursor?.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME)
                logDebug("FileViewModel # file path $strFilePath / $strFileSize")
                if (strFilePath != null) {
                    filePath.set(strFilePath)
                }
            }
            cursor?.close()*/

            val strFileName = context.getString(R.string.file_name, MyFileUtils.getFileName(context, uri))
            val strFileSize = context.getString(R.string.file_size, MyFileUtils.getFileSize(context, uri))
            fileName.set(strFileName)
            fileSize.set(strFileSize)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}