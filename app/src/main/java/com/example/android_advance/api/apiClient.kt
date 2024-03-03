package com.example.android_advance.api

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class APIClient @Inject constructor(private val context: Context) {
    private var retrofit: Retrofit? = null

    public fun client(): Retrofit? {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient =
            OkHttpClient.Builder().addInterceptor(interceptor)
                .addInterceptor(appInterceptor(context)).build()
        retrofit = Retrofit.Builder()
            .baseUrl(apiConstant.baseApi)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit
    }
}