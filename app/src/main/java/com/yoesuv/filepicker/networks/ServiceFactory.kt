package com.yoesuv.filepicker.networks


import com.yoesuv.filepicker.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ServiceFactory {

    private fun retrofitInstance(baseUrl: String): Retrofit {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(clientBuilder.build())
            .build()
    }

    fun <T> getApiService(baseUrl: String, service: Class<T>): T {
        return retrofitInstance(baseUrl).create(service)
    }

}