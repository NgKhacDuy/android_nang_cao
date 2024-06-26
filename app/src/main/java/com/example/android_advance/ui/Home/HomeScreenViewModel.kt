package com.example.android_advance.ui.Home

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_advance.api.APIClient
import com.example.android_advance.api.ApiInterface
import com.example.android_advance.api.ApiResponse
import com.example.android_advance.database.DatabaseHelper
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.model.response.messageDto
import com.example.android_advance.model.response.roomDto
import com.example.android_advance.redux.AddRoomDto
import com.example.android_advance.redux.AddUser
import com.example.android_advance.redux.Store
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
    private val _onNewUser = MutableLiveData<UserDto>()

    private lateinit var db: DatabaseHelper

    private var socketManager = SocketManager.getInstance(context)
    var isRefreshing = mutableStateOf(false)
    val onNewRoom: LiveData<List<roomDto>> get() = _onNewRoom
    val onNewUser: LiveData<UserDto> get() = _onNewUser
    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    var store = Store.getStore()
    val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun swipe() = viewModelScope.launch {
        isRefreshing.value = true
        getRoomForUser()
    }

    fun getUserFromSqlite(): UserDto {
        return db.getUser()[0]
    }

    init {
        db = DatabaseHelper(context)
        getUserInfo()
        getRoomForUser()
    }

    suspend fun decodeJson(data: Any): List<roomDto> {
        return coroutineScope.async(Dispatchers.Default) {
            val listType = object : TypeToken<List<roomDto>>() {}.type
            gson.fromJson<List<roomDto>>(data.toString(), listType)
        }.await()
    }


    fun getRoomForUser() {
        try {
            socketManager.disconnect()
            socketManager.connect()
            socketManager.on("rooms") { args ->
                args.let { d ->
                    if (d.isNotEmpty() && d[0] != "rooms") {
                        Log.e("room", "room")
                        val data = d[0]
                        if (data.toString().isNotEmpty()) {
                            coroutineScope.launch {
                                _onNewRoom.postValue(decodeJson(data))
                            }

                        }
                    } else {
                        Log.e("empty room", "room")
                        _onNewRoom.postValue(emptyList())
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
                    response.body()?.data?.let {
                        db.insertUser(it)
                        _onNewUser.postValue(it)
                        store!!.dispatch(AddUser(it))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorJsonObject = Gson().fromJson(errorBody, JsonObject::class.java)
                    val message = errorJsonObject.get("message").asString
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

    fun storeRoomDto(roomDto: roomDto) {
        store!!.dispatch(AddRoomDto(roomDto))
    }
}