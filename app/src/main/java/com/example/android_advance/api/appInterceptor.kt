package com.example.android_advance.api

import android.content.Context
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.android_advance.database.DatabaseHelper
import com.example.android_advance.model.request.RefreshRequest
import com.example.android_advance.model.response.SigninResponse
import com.example.android_advance.shared_preference.AppSharedPreference
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.io.IOException
import javax.inject.Inject

class appInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
) : Interceptor {
    private lateinit var db: DatabaseHelper
    val appSharedPreference = AppSharedPreference(context)

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var originRequest = chain.request()
        var originResponse = chain.proceed(originRequest)
        val refreshRequest = RefreshRequest(refreshToken = appSharedPreference.refreshToken)
        if (originResponse.code == 401) {
            val body =
                Gson().toJson(refreshRequest).toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val authRequest = chain.request().newBuilder()
                .removeHeader("Authorization")
                .url(apiConstant.baseApi + apiConstant.userRefresh)
                .post(body)
                .build()
            val tokenResponse = chain.proceed(authRequest)
            if (tokenResponse.code == 200) {
                originResponse.close()
                var json = JSONObject(tokenResponse.body?.string()!!)
                val data = JSONObject(json.getString("data"))
                val accessToken = data.getString("accessToken")
                appSharedPreference.accessToken = accessToken
                val temp =
                    chain.proceed(
                        originRequest.newBuilder()
                            .removeHeader("Authorization")
                            .addHeader("Authorization", "Bearer $accessToken").build()
                    )
                originResponse = temp
            } else {
                deleteToken()
                deleteSqlite()
                val navController = NavController(context)
                navController.navigate(route = "auth") {
                    popUpTo("home") {
                        inclusive = true
                    }
                }
            }
        }
        return originResponse
    }

    fun deleteToken() {
        appSharedPreference.refreshToken = ""
        appSharedPreference.accessToken = ""
    }

    fun deleteSqlite() {
        db = DatabaseHelper(context)
        db.deleteUser()
    }
}