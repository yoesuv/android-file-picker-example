package com.yoesuv.filepicker.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class MyFileUtils {

    companion object {

        fun getFileName(context: Context, uri: Uri): String? {
            try {
                val cursor = context.contentResolver.query(uri, null, null, null, null)
                cursor?.moveToFirst()
                val fileName = cursor?.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
                cursor?.close()
                return fileName
            } catch (e: Exception) {
                throw e
            }
        }

        fun copyToTempFile(inputStream: InputStream?, file: File) {
            try {
                val outputStream = FileOutputStream(file)
                outputStream.write(inputStream?.readBytes())
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

}