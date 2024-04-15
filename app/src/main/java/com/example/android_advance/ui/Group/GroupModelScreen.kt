package com.example.android_advance.ui.Group

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_advance.api.APIClient
import com.example.android_advance.api.ApiInterface
import com.example.android_advance.api.ApiResponse
import com.example.android_advance.api.ApiResponse.*
import com.example.android_advance.database.DatabaseHelper
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
import kotlin.collections.ArrayList

@HiltViewModel
class GroupModelScreen @Inject constructor(@ApplicationContext private val context: Context) : ViewModel() {
    private val appSharedPreference = AppSharedPreference(context)
    private val addedFriendIds = mutableListOf<String>()
    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    val socketManager = SocketManager.getInstance(context)
    private val db: DatabaseHelper = DatabaseHelper(context)
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

    fun createRoom(groupName: String): Boolean {
        val currentUserId = db.getUserId()
        currentUserId?.let { userId ->
            var createGroupSuccess = true
            if (addedFriendIds.size < 3) {
                Log.e("RoomCreation", "Vui lòng phải có ít nhất 3 thành viên trong nhóm.")
                showToast("Vui lòng phải có ít nhất 3 thành viên trong nhóm")
                createGroupSuccess = false
            }
            if (groupName.isEmpty()) {
                Log.e("RoomCreation", "Vui lòng nhập tên nhóm.")
                showToast("Vui lòng nhập tên nhóm")
                createGroupSuccess = false
            }

            if (createGroupSuccess) {
                val listUser = ArrayList(addedFriendIds)
                listUser.add(userId)
                val roomRequest = RoomRequest(listUser, groupName)
                Log.d("SocketCallback", "Creating room with group name: $groupName")
                Log.d("SocketCallback", "List of user IDs in the room: $listUser")
                socketManager.emit("create_room", gson.toJson(roomRequest))
                return true
            } else {
                return false
            }

        } ?: run {
            Log.e("RoomCreation", "Failed to get current user ID.")
            return false
        }
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun performSearch(keyword: String) {
        val apiClient: APIClient = APIClient(context)
        val apiService = apiClient.client()?.create(ApiInterface::class.java)
        val call = apiService?.Search("Bearer ${appSharedPreference.accessToken}", keyword)
        call?.enqueue(object : Callback<ApiResponse.BaseApiResponse<SearchDto>> {
            override fun onResponse(
                call: Call<ApiResponse.BaseApiResponse<SearchDto>>,
                response: Response<ApiResponse.BaseApiResponse<SearchDto>>
            ) {
                if (response.isSuccessful) {
                    val searchResults = response.body()?.data?.listUserDto?.map { userDto ->
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
                call: Call<ApiResponse.BaseApiResponse<SearchDto>>,
                t: Throwable
            ) {
                Log.d("SearchError", t.message.toString())
            }

        })
    }
}