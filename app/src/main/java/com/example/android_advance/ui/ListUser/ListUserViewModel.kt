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
import com.example.android_advance.model.response.SearchDto
import com.example.android_advance.model.response.SearchResponse
import com.example.android_advance.redux.Store
import com.example.android_advance.shared_preference.AppSharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ListUserViewModel @Inject constructor(@ApplicationContext private val context: Context) : ViewModel() {
    private val appSharedPreference = AppSharedPreference(context)
    val store = Store.getStore()
    val roomDto = store?.state?.roomDto
    val userDto = store?.state?.userDto!!

    private val addedFriendIds = mutableListOf<String>()
    private val _searchResult = MutableLiveData<List<SearchResponse>?>()
    val search = mutableStateOf("")
    val searchResults: LiveData<List<SearchResponse>?> get() = _searchResult

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
                                friends = it,
                                avatar = userDto.avatar
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