package com.yoesuv.filepicker.menu.download.viewmodels

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.webkit.URLUtil
import androidx.lifecycle.ViewModel
import com.yoesuv.filepicker.data.DOWNLOAD_LINK
import com.yoesuv.filepicker.utils.logDebug
import java.io.File

class DownloadViewModel: ViewModel() {

    // https://stackoverflow.com/a/68627407/3559183
    fun downloadFile(context: Context) {
        logDebug("DownloadViewModel # start download file")
        val fileName = URLUtil.guessFileName(DOWNLOAD_LINK, null, null)
        logDebug("DownloadViewModel # file name : $fileName")
        try {
            val folder = File(Environment.getExternalStorageDirectory(), "Download")
            logDebug("DownloadViewModel # folder exist? ${folder.exists()}")
            if (!folder.exists()) {
                folder.mkdirs()
            }
            val path = folder.absolutePath + File.separator + fileName
            logDebug("DownloadViewModel # file path : $path")
            val fileResult = File(path)

            val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val request = DownloadManager.Request(Uri.parse(DOWNLOAD_LINK))
            request.setAllowedOverMetered(true)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            request.setTitle(fileName)
            request.setDestinationUri(Uri.fromFile(fileResult))

            downloadManager.enqueue(request)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}