package com.example.android_advance.ui.ListUser

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_advance.api.APIClient
import com.example.android_advance.api.ApiInterface
import com.example.android_advance.api.ApiResponse
import com.example.android_advance.model.request.AddUserToGroupRequest
import com.example.android_advance.model.request.RemoveUserOutOfGroup
import com.example.android_advance.model.response.SearchDto
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.model.response.roomDto
import com.example.android_advance.redux.AddUserToListGroup
import com.example.android_advance.redux.RemoveUserOutOfGroupAction
import com.example.android_advance.redux.Store
import com.example.android_advance.shared_preference.AppSharedPreference
import com.example.android_advance.socketio.SocketManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ListUserViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {
    private val appSharedPreference = AppSharedPreference(context)
    val store = Store.getStore()
    val roomDto = store?.state?.roomDto
    val userDto = store?.state?.userDto!!

    private val addedFriendIds = mutableListOf<String>()
    private val addedListUser = mutableListOf<UserDto>()
    private val _searchResult = MutableLiveData<List<UserDto>?>()
    val search = mutableStateOf("")
    val searchResults: LiveData<List<UserDto>?> get() = _searchResult

    var gson: Gson = GsonBuilder()
        .setLenient()
        .create()
    val socketManager = SocketManager.getInstance(context)

    init {
        socketManager.connect()
    }

    fun addUserToGroup() {
        try {
            if (addedFriendIds.isNotEmpty()) {
                val addedFriendIdsArrayList = ArrayList<String>(addedFriendIds)
                val addUserToGroupRequest = AddUserToGroupRequest(
                    idRoom = roomDto?.id!!,
                    idUser = addedFriendIdsArrayList
                )
                socketManager.emit("add_user_to_group", gson.toJson(addUserToGroupRequest))
                store!!.dispatch(AddUserToListGroup(ArrayList(addedListUser)))
            }
        } catch (e: Exception) {
            Log.e("AddUserToGroup", e.message.toString())
        }
    }

    fun removeUser(user: UserDto) {
        try {
            val removeUserOutOfGroupRequest = RemoveUserOutOfGroup(
                idRoom = roomDto?.id!!,
                idUser = user.id!!
            )
            socketManager.emit("remove_user", gson.toJson(removeUserOutOfGroupRequest))
            store!!.dispatch(RemoveUserOutOfGroupAction(user))
        } catch (e: Exception) {
            Log.e("RemoveUserOutOfGroup", e.message.toString())
        }
    }

    fun addFriendId(user: UserDto) {
        addedFriendIds.add(user.id!!)
        addedListUser.add(user)
    }

    fun removeFriendId(user: UserDto) {
        addedFriendIds.remove(user.id!!)
        addedListUser.remove(user)
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
                            UserDto(
                                id = userDto.id,
                                name = userDto.name,
                                phoneNumber = userDto.phoneNumber,
                                createAt = userDto.createAt,
                                updateAt = userDto.updateAt,
                                deletedAt = userDto.deletedAt,
                                friends = it,
                                avatar = userDto.avatar
                            )
                        }
                    }
                    _searchResult.postValue(searchResults as List<UserDto>?)
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