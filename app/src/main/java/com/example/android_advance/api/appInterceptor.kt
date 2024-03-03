package com.example.android_advance.api

import android.content.Context
import android.util.Log
import com.example.android_advance.model.request.RefreshRequest
import com.example.android_advance.model.response.SigninResponse
import com.example.android_advance.shared_preference.AppSharedPreference
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Call
import retrofit2.Callback
import java.io.IOException
import javax.inject.Inject

class appInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val originResponse = chain.proceed(request)
        if (originResponse.code == 401) {
            val newToken = refreshTokenFunction()
            if (newToken != "") {
                request = request.newBuilder().header("Authorization", "Bearer $newToken").build()
                return chain.proceed(request)
            }
        }
        return originResponse
    }

    private fun refreshTokenFunction(): String {
        val appSharedPreference = AppSharedPreference(context)
        val refreshRequest = RefreshRequest(refreshToken = appSharedPreference.refreshToken)
        val apiService = APIClient.client?.create(ApiInterface::class.java)
        val call = apiService?.refresh(refreshRequest)
        call?.enqueue(object : Callback<ApiResponse.BaseApiResponse<SigninResponse>> {
            override fun onResponse(
                call: Call<ApiResponse.BaseApiResponse<SigninResponse>>,
                response: retrofit2.Response<ApiResponse.BaseApiResponse<SigninResponse>>
            ) {
                if (response.isSuccessful) {
                    appSharedPreference.accessToken = response.body()?.data?.accessToken.toString()
                } else {
                    Log.e("REFRESH", "REFRESH FAILED")
                }
            }

            override fun onFailure(
                call: Call<ApiResponse.BaseApiResponse<SigninResponse>>,
                t: Throwable
            ) {
                Log.e("Signup", "Error: $t")
            }

        })
        return appSharedPreference.refreshToken
    }
}