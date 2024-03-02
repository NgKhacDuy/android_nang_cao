package com.example.android_advance.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


internal object APIClient {
    private var retrofit: Retrofit? = null
    val client: Retrofit?
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient =
                OkHttpClient.Builder().addInterceptor(interceptor)
                    .addNetworkInterceptor(appInterceptor()).build()
            retrofit = Retrofit.Builder()
                .baseUrl(apiConstant.baseApi)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit
        }
}