package com.yoesuv.filepicker.menu.download.viewmodels

import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.webkit.URLUtil
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.data.DOWNLOAD_LINK
import com.yoesuv.filepicker.networks.DownloadRepository
import java.io.File

class DownloadViewModel: ViewModel() {

    private val repoDownload = DownloadRepository(viewModelScope)

    // https://stackoverflow.com/a/68627407/3559183
    fun downloadFile(context: Context) {
        val fileName = URLUtil.guessFileName(DOWNLOAD_LINK, null, null)
        try {
            val folder = File(Environment.getExternalStorageDirectory(), "Download")
            if (!folder.exists()) {
                folder.mkdirs()
            }
            val pathFile = folder.absolutePath + File.separator + fileName
            val fileResult = File(pathFile)

            val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val request = DownloadManager.Request(Uri.parse(DOWNLOAD_LINK))
            request.setAllowedOverMetered(true)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setTitle(fileName)
            request.setDestinationUri(Uri.fromFile(fileResult))
            downloadManager.enqueue(request)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*
     https://stackoverflow.com/a/62879112/3559183
     https://stackoverflow.com/a/61093017/3559183
    */
    @RequiresApi(Build.VERSION_CODES.Q)
    fun downloadFileSdk29(context: Context) {
        val fileName = URLUtil.guessFileName(DOWNLOAD_LINK, null, null)
        repoDownload.downloadFile(DOWNLOAD_LINK, { body ->
            if (body != null) {
                val fileCollection = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                val contentValues = ContentValues()
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                val uri = context.contentResolver.insert(fileCollection, contentValues)
                if (uri != null) {
                    val outputStream = context.contentResolver.openOutputStream(uri, "rwt")
                    outputStream?.write(body.bytes())
                    outputStream?.close()
                    Toast.makeText(context, R.string.toast_download_success, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, R.string.toast_download_failed, Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(context, R.string.toast_download_failed, Toast.LENGTH_LONG).show()
            }
        }, { error ->
            error.printStackTrace()
            Toast.makeText(context, R.string.toast_download_failed, Toast.LENGTH_LONG).show()
        })
    }

}