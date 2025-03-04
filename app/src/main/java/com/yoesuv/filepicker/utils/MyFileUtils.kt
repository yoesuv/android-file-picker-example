package com.yoesuv.filepicker.utils

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import io.ktor.utils.io.ByteReadChannel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.DecimalFormat


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

fun getFileSize(context: Context, uri: Uri): String {
    try {
        var output = ""
        val decimalFormat = DecimalFormat("#.##")
        val unitKiloByte = 1000.0
        val unitMegaByte = unitKiloByte * 1000.0
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndexOrThrow(OpenableColumns.SIZE)
            val fileSize: Double = cursor.getLong(columnIndex).toDouble()
            cursor.close()
            if (fileSize > 0 && fileSize < unitKiloByte) {
                output = "$fileSize B"
            } else if (fileSize >=unitKiloByte && fileSize < unitMegaByte) {
                output = "${decimalFormat.format(fileSize/unitKiloByte)} kB"
            } else {
                output = "${decimalFormat.format(fileSize/unitMegaByte)} MB"
            }
        }
        return output
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

suspend fun ByteReadChannel.copyTo(outputStream: OutputStream) {
    val buffer = ByteArray(8192)
    while (true) {
        val read = readAvailable(buffer, 0, buffer.size)
        if (read == -1) break
        outputStream.write(buffer, 0, read)
    }
}