package com.example.android_advance.ui.Home

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.android_advance.api.APIClient
import com.example.android_advance.api.ApiInterface
import com.example.android_advance.api.ApiResponse
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.shared_preference.AppSharedPreference
import com.example.android_advance.socketio.SocketManager
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel()
class HomeScreenViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {
    private val appSharedPreference = AppSharedPreference(context)

    fun getRoomForUser() {
        try {
            val socketManager = SocketManager(context)
            socketManager.connect()
            socketManager.on("Error") {}
        } catch (e: Exception) {
            Log.e("EXCEPTION", e.message.toString())
        }
    }

    fun getUserInfo() {
        val apiClient: APIClient = APIClient(context)
        val apiService = apiClient.client()?.create(ApiInterface::class.java)
        val call = apiService?.profile("Bearer ${appSharedPreference.accessToken}")
        call?.enqueue(object : Callback<ApiResponse.BaseApiResponse<UserDto>> {
            override fun onResponse(
                call: Call<ApiResponse.BaseApiResponse<UserDto>>,
                response: Response<ApiResponse.BaseApiResponse<UserDto>>
            ) {
                if (response.isSuccessful) {
                    Log.e("USER INFO", response.body()?.data?.name.toString())
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorJsonObject = Gson().fromJson(errorBody, JsonObject::class.java)
                    val message = errorJsonObject.get("message").asString
                    Log.e("USER INFO", message)
                }
            }

            override fun onFailure(call: Call<ApiResponse.BaseApiResponse<UserDto>>, t: Throwable) {
                Log.e("USER INFO ERROR", t.message.toString())
            }

        })
    }
}