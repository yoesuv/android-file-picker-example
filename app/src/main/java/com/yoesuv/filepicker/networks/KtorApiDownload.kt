package com.yoesuv.filepicker.networks

import android.content.Context
import com.yoesuv.filepicker.utils.copyTo
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.util.InternalAPI
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object KtorApiDownload {

    @OptIn(InternalAPI::class)
    suspend fun downloadFile(context: Context, link: String, fileName: String): File? {
        val client = HttpClient(Android)
        try {
            val fileResponse: HttpResponse = client.get(link)
            val channel: ByteReadChannel = fileResponse.content
            val file = File(context.filesDir, fileName)
            withContext(Dispatchers.IO) {
                file.outputStream().use { outputStream ->
                    channel.copyTo(outputStream)
                }
            }
            return file
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        } finally {
            client.close()
        }
    }

}