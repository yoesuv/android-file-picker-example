package com.yoesuv.filepicker.menu.file.viewmodels

import android.content.Context
import android.net.Uri
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.utils.MyFileUtils
import com.yoesuv.filepicker.utils.logDebug
import java.io.File

class FileViewModel: ViewModel() {

    var filePath: ObservableField<String> = ObservableField()
    var fileName: ObservableField<String> = ObservableField()
    var fileSize: ObservableField<String> = ObservableField()

    fun setSelectedFile(context: Context, uri: Uri) {
        logDebug("FileViewModel # setSelectedFile ${uri.path}")
        try {
            val strFileName = context.getString(R.string.file_name, MyFileUtils.getFileName(context, uri))
            val strFileSize = context.getString(R.string.file_size, MyFileUtils.getFileSize(context, uri))
            fileName.set(strFileName)
            fileSize.set(strFileSize)

            val cacheDir = context.cacheDir.path + File.separator
            val inputStream = context.contentResolver.openInputStream(uri)
            val tempFile = File(cacheDir + MyFileUtils.getFileName(context, uri))
            MyFileUtils.copyToTempFile(inputStream, tempFile)
            filePath.set(tempFile.absolutePath)

            inputStream?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}