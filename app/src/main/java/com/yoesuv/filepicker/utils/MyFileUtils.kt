package com.yoesuv.filepicker.utils

import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class MyFileUtils {

    companion object {

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