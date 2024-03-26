package com.example.android_advance.ui.Group

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_advance.api.APIClient
import com.example.android_advance.api.ApiInterface
import com.example.android_advance.api.ApiResponse
import com.example.android_advance.api.ApiResponse.*
import com.example.android_advance.model.request.RoomRequest
import com.example.android_advance.model.response.*
import com.example.android_advance.shared_preference.AppSharedPreference
import com.example.android_advance.socketio.SocketManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

@HiltViewModel
class GroupScreenModel  @Inject constructor(@ApplicationContext private val context: Context) : ViewModel(){
    private val appSharedPreference = AppSharedPreference(context)
    private val addedFriendIds = mutableListOf<String>()
    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    val socketManager = SocketManager.getInstance(context)
    private val _searchResult = MutableLiveData<List<SearchResponse>?>()
    val search = mutableStateOf("")
    val searchResults: LiveData<List<SearchResponse>?> get() = _searchResult
    fun getUserInfo(): MutableLiveData<UserDto?> {
        val apiClient: APIClient = APIClient(context)
        val apiService = apiClient.client()?.create(ApiInterface::class.java)
        val liveData = MutableLiveData<UserDto?>()
        val call = apiService?.profile("Bearer ${appSharedPreference.accessToken}")
        call?.enqueue(object : Callback<BaseApiResponse<UserDto>> {
            override fun onResponse(
                call: Call<BaseApiResponse<UserDto>>,
                response: Response<BaseApiResponse<UserDto>>
            ) {
                if (response.isSuccessful) {
                    Log.e("USER INFO", response.body().toString())
                    val userDtoData = response.body()?.data
                    if (userDtoData != null) {
                        liveData.postValue(userDtoData)
                    }
                } else {
                    liveData.postValue(null)
                    Log.e("USER INFO", "error")
                }
            }

            override fun onFailure(call: Call<BaseApiResponse<UserDto>>, t: Throwable) {
                Log.e("USER INFO ERROR", t.message.toString())
            }
        })
        return liveData
    }

    fun getFriendInfo(): MutableLiveData<List<FriendResponse?>?> {
        val apiClient: APIClient = APIClient(context)
        val apiService = apiClient.client()?.create(ApiInterface::class.java)
        val liveData = MutableLiveData<List<FriendResponse?>?>()
        val call = apiService?.friend("Bearer ${appSharedPreference.accessToken}")
        call?.enqueue(object : Callback<ApiResponse.BaseApiResponse<List<FriendResponse>>> {
            override fun onResponse(
                call: Call<ApiResponse.BaseApiResponse<List<FriendResponse>>>,
                response: Response<ApiResponse.BaseApiResponse<List<FriendResponse>>>
            ) {
                if (response.isSuccessful) {
                    val userDtoData = response.body()?.data?.map { userDto ->
                        FriendResponse(
                            user = userDto.user,
                            status = userDto.status
                        )
                    }
                    if (userDtoData != null) {
                        liveData.postValue(userDtoData)
                    }
                } else {
                    liveData.postValue(null)
                    Log.e("FRIEND INFO", "error")
                }
            }

            override fun onFailure(call: Call<ApiResponse.BaseApiResponse<List<FriendResponse>>>, t: Throwable) {
                Log.e("FRIEND INFO ERROR", t.message.toString())
            }
        })
        return liveData
    }

    fun addFriendId(userId: String) {
        addedFriendIds.add(userId)
        Log.d("Temporary Friend IDs", "Added friend with ID: $userId")
        Log.d("Temporary Friend IDs", "Current list of friend IDs: $addedFriendIds")
    }

    fun removeFriendId(userId: String) {
        if (addedFriendIds.remove(userId)) {
            Log.d("RemovedFriendId", "Removed friend with ID: $userId")
        } else {
            Log.d("RemovedFriendId", "Friend with ID $userId not found in the list")
        }
    }

    fun getAddedFriendIds(): List<String> = addedFriendIds


    fun createRoom() {
        val roomId = UUID.randomUUID().toString()
        val roomRequest = RoomRequest(roomId)
        socketManager.emit("create_room", gson.toJson(roomRequest))
        socketManager.on("create_room_result") { args ->
            Log.d("SocketCallback", "Received create_room_result callback")

            if (args.isNotEmpty()) {
                Log.d("SocketCallback", "Data received in callback: $args")
                val data = args[0]
                if (data != null && data.toString().isNotEmpty()) {
                    Log.d("SocketCallback", "Non-empty data received: $data")
                    val roomDto = gson.fromJson(data.toString(), roomDto::class.java)
                    if (roomDto.id != null) {
                        Log.d("SocketCallback", "Room created successfully")
                        val friendIds = roomDto.listUsers
                        val currentUser = getUserInfo().value?.id ?: ""
                        if (!friendIds.contains(currentUser)) {
                            friendIds.add(currentUser)
                        }

                        Log.d("SocketCallback", "Room ID: ${roomDto.id}")
                        Log.d("SocketCallback", "Participants in Room: $friendIds")
                        val roomParticipants = RoomParticipants(roomDto.id!!, friendIds)
                        socketManager.emit("add_participants", gson.toJson(roomParticipants))
                    } else {
                        Log.e("SocketCallback", "Failed to create room")
                    }
                } else {
                    Log.e("SocketCallback", "Empty or null data received")
                }
            } else {
                Log.e("SocketCallback", "No data received in callback")
            }
        }
    }

    fun performSearch(keyword: String) {
        val apiClient: APIClient = APIClient(context)
        val apiService = apiClient.client()?.create(ApiInterface::class.java)
        val call = apiService?.Search("Bearer ${appSharedPreference.accessToken}", keyword)
        call?.enqueue(object : Callback<ApiResponse.BaseApiResponse<List<UserDto>>> {
            override fun onResponse(
                call: Call<ApiResponse.BaseApiResponse<List<UserDto>>>,
                response: Response<ApiResponse.BaseApiResponse<List<UserDto>>>
            ) {
                if (response.isSuccessful) {
                    val searchResults = response.body()?.data?.map { userDto ->
                        userDto.friends?.let {
                            SearchResponse(
                                id = userDto.id,
                                name = userDto.name,
                                phoneNumber = userDto.phoneNumber,
                                createAt = userDto.createAt,
                                updateAt = userDto.updateAt,
                                deletedAt = userDto.deletedAt,
                                friends = it
                            )
                        }
                    }
                    _searchResult.postValue(searchResults as List<SearchResponse>?)
                }
                Log.d("Search", response.body()?.data.toString())
            }

            override fun onFailure(
                call: Call<ApiResponse.BaseApiResponse<List<UserDto>>>,
                t: Throwable
            ) {
                Log.d("SearchError", t.message.toString())
            }

        })
    }


    data class RoomParticipants(
        val roomId: String,
        val participantIds: List<String>
    )


}

