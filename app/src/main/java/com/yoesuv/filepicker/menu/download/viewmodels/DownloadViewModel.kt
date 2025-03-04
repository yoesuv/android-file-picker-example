package com.yoesuv.filepicker.menu.download.viewmodels

import android.app.Activity
import android.app.DownloadManager
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.webkit.URLUtil
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoesuv.filepicker.R
import com.yoesuv.filepicker.data.DOWNLOAD_LINK
import com.yoesuv.filepicker.data.DOWNLOAD_LINK_FULL
import com.yoesuv.filepicker.networks.DownloadRepository
import com.yoesuv.filepicker.networks.KtorApiDownload
import com.yoesuv.filepicker.utils.logError
import com.yoesuv.filepicker.utils.showSnackbarError
import com.yoesuv.filepicker.utils.showSnackbarSucces
import kotlinx.coroutines.launch
import java.io.File

class DownloadViewModel : ViewModel() {

    private val repoDownload = DownloadRepository(viewModelScope)

    // https://stackoverflow.com/a/68627407/3559183
    fun downloadFile(activity: Activity) {
        val fileName = URLUtil.guessFileName(DOWNLOAD_LINK_FULL, null, null)
        try {
            val folder = File(Environment.getExternalStorageDirectory(), "Download")
            if (!folder.exists()) {
                folder.mkdirs()
            }
            val pathFile = folder.absolutePath + File.separator + fileName
            val fileResult = File(pathFile)

            val downloadManager =
                activity.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val request = DownloadManager.Request(Uri.parse(DOWNLOAD_LINK_FULL))
            request.setAllowedOverMetered(true)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setTitle(fileName)
            request.setDestinationUri(Uri.fromFile(fileResult))
            downloadManager.enqueue(request)
        } catch (e: Exception) {
            activity.showSnackbarError(R.string.toast_download_failed)
            logError(e.message)
            e.printStackTrace()
        }
    }

    /*
     https://stackoverflow.com/a/62879112/3559183
     https://stackoverflow.com/a/61093017/3559183
    */
    @RequiresApi(Build.VERSION_CODES.Q)
    fun downloadFileSdk29(activity: Activity) {
        val fileName = URLUtil.guessFileName(DOWNLOAD_LINK, null, null)
        repoDownload.downloadFile(DOWNLOAD_LINK, { body ->
            if (body != null) {
                val fileCollection =
                    MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
                val contentValues = ContentValues()
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                val uri = activity.contentResolver.insert(fileCollection, contentValues)
                if (uri != null) {
                    val outputStream = activity.contentResolver.openOutputStream(uri, "rwt")
                    outputStream?.write(body.bytes())
                    outputStream?.close()
                    activity.showSnackbarSucces(R.string.toast_download_success)
                } else {
                    activity.showSnackbarError(R.string.toast_download_failed)
                }
            } else {
                activity.showSnackbarError(R.string.toast_download_failed)
            }
        }, { error ->
            logError(error.message)
            error.printStackTrace()
            activity.showSnackbarError(R.string.toast_download_failed)
        })
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun downloadFileKtor(activity: Activity) {
        val fileName = URLUtil.guessFileName(DOWNLOAD_LINK, null, null)
        viewModelScope.launch {
            val fileCollection = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            val theFile = KtorApiDownload.downloadFile(activity, DOWNLOAD_LINK_FULL, fileName)
            if (theFile != null) {
                val uri = activity.contentResolver.insert(fileCollection, contentValues)
                if (uri != null) {
                    activity.contentResolver.update(uri, contentValues, null, null)
                    val outputStream = activity.contentResolver.openOutputStream(uri, "rwt")
                    outputStream?.write(theFile.readBytes())
                    outputStream?.close()
                    activity.showSnackbarSucces(R.string.toast_download_success)
                } else {
                    activity.showSnackbarError(R.string.toast_download_failed)
                }
            } else {
                activity.showSnackbarError(R.string.toast_download_failed)
            }
        }
    }

}