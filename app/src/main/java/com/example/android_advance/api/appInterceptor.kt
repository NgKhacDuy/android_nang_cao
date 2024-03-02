package com.example.android_advance.api

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class appInterceptor(private val refreshTokenFunction: () -> String?) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val originResponse = chain.proceed(request)
        if (originResponse.code == 401) {
            val newToken = refreshTokenFunction()
            if (newToken != null) {
                request = request.newBuilder().header("Authorization", "Bearer $newToken").build()
                return chain.proceed(request)
            }
        }
        return originResponse
    }
}