package com.yoesuv.filepicker.networks

import com.yoesuv.filepicker.data.BASE_URL
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class DownloadRepository(private val scope: CoroutineScope) : SafeApiRequest() {

    private val restApi = ServiceFactory.getApiService(BASE_URL, RestApiDownload::class.java)

    fun downloadFile(link: String, onSuccess: (ResponseBody?) -> Unit, onError: (Exception) -> Unit) {
        scope.launch {
            try {
                val result = apiRequest { restApi.downloadFile(link) }
                onSuccess(result)
            } catch (e: Exception) {
                if (e !is CancellationException) {
                    onError(e)
                }
            }
        }
    }

}