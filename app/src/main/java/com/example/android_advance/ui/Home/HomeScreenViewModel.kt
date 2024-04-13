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
import com.example.android_advance.firebase.FirebaseMessageManagement
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.model.response.roomDto
import com.example.android_advance.shared_preference.AppSharedPreference
import com.example.android_advance.socketio.SocketManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel()
class HomeScreenViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {
    private val appSharedPreference = AppSharedPreference(context)

    private val _onNewRoom = MutableLiveData<List<roomDto>>()

    private var db: DatabaseHelper

    private var socketManager = SocketManager.getInstance(context)
    var isRefreshing = mutableStateOf(false)
    val onNewRoom: LiveData<List<roomDto>> get() = _onNewRoom
    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    val roomUsersMap = mutableMapOf<String, List<UserDto>>()
    private val _userList = MutableLiveData<List<UserDto>>()
    val userList: LiveData<List<UserDto>> get() = _userList

    fun swipe() = viewModelScope.launch {
        isRefreshing.value = true
        getRoomForUser()
    }

    init {
        db = DatabaseHelper(context)
        getUserInfo()
        getRoomForUser()
    }
    val userId = db.getUserId()

    fun getRoomForUser() {
        try {
            socketManager.disconnect()
            socketManager.connect()
            socketManager.on("rooms") { args ->
                args.let { d ->
                    if (d.isNotEmpty()) {
                        val data = d[0]
                        Log.e("DATA", data.toString())
                        if (data.toString().isNotEmpty()) {
                            val listType = object : TypeToken<List<roomDto>>() {}.type
                            val temp: List<roomDto> = gson.fromJson(data.toString(), listType)
                            _onNewRoom.postValue(temp)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("EXCEPTION", e.message.toString())
        }
    }

    fun updateUserList(newList: List<UserDto>) {
        _userList.value = newList
        Log.d("UserListUpdate", "Updated user list: $newList")
        for (user in newList) {
            Log.d("UserListUpdate", "User ID: ${user.id}, User Name: ${user.name}")
        }
    }

    fun getRoomDetails(roomId: String) {
        val room = onNewRoom.value?.find { it.id == roomId }
        if (room != null) {
            Log.d("Room Details", "Room ID: ${room.id}, Room Name: ${room.name}")
            val userList = room.user
            if (!userList.isNullOrEmpty()) {
                Log.d("Room Details", "List of Users:")
                for (user in userList) {
                    Log.d("Room Details", "User ID: ${user.id}, User Name: ${user.name}")
                }
                updateUserList(userList)
            } else {
                Log.d("Room Details", "No users found in this room.")
            }
            val jsonObject = JSONObject().apply {
                put("roomId", roomId)
            }

            socketManager.emit("rooms", jsonObject.toString())
        } else {
            Log.d("Room Details", "Room with ID $roomId not found!")
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
                    val firebaseMessage = FirebaseMessageManagement()
                    firebaseMessage.sendTokenWithUserId(db.getUser().first().id!!)
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
}