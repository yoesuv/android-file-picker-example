package com.yoesuv.filepicker.networks

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface RestApiDownload {

    @Streaming
    @GET
    suspend fun downloadFile(@Url link: String): Response<ResponseBody>

}