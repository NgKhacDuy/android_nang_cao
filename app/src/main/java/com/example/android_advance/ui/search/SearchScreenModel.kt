package com.example.android_advance.ui.search

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_advance.api.APIClient
import com.example.android_advance.api.ApiInterface
import com.example.android_advance.api.ApiResponse
import com.example.android_advance.model.response.SearchResponse
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.shared_preference.AppSharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SearchScreenModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {
    private val _searchResult = MutableLiveData<List<SearchResponse>?>()
    val search = mutableStateOf("")
    val searchResults: LiveData<List<SearchResponse>?> get() = _searchResult
    private val appSharedPreference = AppSharedPreference(context)
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
}