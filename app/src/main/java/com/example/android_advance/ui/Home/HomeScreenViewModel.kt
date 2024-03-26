package com.example.android_advance.ui.Home

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_advance.api.APIClient
import com.example.android_advance.api.ApiInterface
import com.example.android_advance.api.ApiResponse
import com.example.android_advance.database.DatabaseHelper
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.model.response.roomDto
import com.example.android_advance.shared_preference.AppSharedPreference
import com.example.android_advance.socketio.SocketManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.StringReader
import javax.inject.Inject


@HiltViewModel()
class HomeScreenViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {
    private val appSharedPreference = AppSharedPreference(context)

    private val _onNewRoom = MutableLiveData<List<roomDto>>()

    private lateinit var db: DatabaseHelper

    private var socketManager = SocketManager.getInstance(context)

    val onNewRoom: LiveData<List<roomDto>> get() = _onNewRoom
    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    init {
        db = DatabaseHelper(context)
        getUserInfo()
        getRoomForUser()
    }


    fun getRoomForUser() {
        try {
            socketManager.connect()
            socketManager.on("rooms") { args ->
                args.let { d ->
                    if (d.isNotEmpty()) {
                        val data = d[0]
                        if (data.toString().isNotEmpty()) {
                            val listType = object : TypeToken<List<roomDto>>() {}.type
                            val temp: List<roomDto> = gson.fromJson(data.toString(), listType)
                            Log.e("ROOMS",temp.toString())
                            _onNewRoom.postValue(temp)
                        }
                    }
                }
            }
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
                    response.body()?.data?.let { db.insertUser(it) }
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

    fun disconnectSocket() {
        socketManager.disconnect()
    }
}