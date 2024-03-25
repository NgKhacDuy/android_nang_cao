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
import com.example.android_advance.data_class.InfoDialog
import com.example.android_advance.database.DatabaseHelper
import com.example.android_advance.model.request.FriendRequest
import com.example.android_advance.model.response.SearchResponse
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.shared_preference.AppSharedPreference
import com.example.android_advance.ui.components.IconType
import com.google.gson.Gson
import com.google.gson.JsonObject
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
    var db: DatabaseHelper = DatabaseHelper(context)
    val infoDialog = mutableStateOf(InfoDialog(fun() {}, fun() {}, "", "", "", ""))
    val isShowDialog = mutableStateOf(false)
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

    fun addFriend(idUser: String, name: String) {
        val apiClient: APIClient = APIClient(context)
        val apiService = apiClient.client()?.create(ApiInterface::class.java)
        val addFriendRequest = FriendRequest(idUser)
        val call = apiService?.addFriend("Bearer ${appSharedPreference.accessToken}", addFriendRequest)
        call?.enqueue(object : Callback<ApiResponse.BaseApiResponse<Unit>> {
            override fun onResponse(
                call: Call<ApiResponse.BaseApiResponse<Unit>>,
                response: Response<ApiResponse.BaseApiResponse<Unit>>
            ) {
                if (response.isSuccessful) {
                    Log.d("Friend", "request successfully")
                    _searchResult.postValue(null)
                    performSearch(name)
                } else {
                    Log.d("Friend", "something wrong")
                }
            }

            override fun onFailure(call: Call<ApiResponse.BaseApiResponse<Unit>>, t: Throwable) {
                Log.d("Friend", "server error")
            }
        })

    }

    fun checkCurrentUser(idToCheck: String): Boolean {
        return (idToCheck == db.getUser().first().id)
    }

    fun performRequestFriend(idUser: String) {
        infoDialog.value = InfoDialog(
            fun() {
                isShowDialog.value = false
                decideFriend(idUser, "Reject")
            },
            fun() {
                isShowDialog.value = false
                decideFriend(idUser, "Accept")
            },
            "Xác nhận",
            "Bạn muốn chấp nhận kết bạn hay từ chối kết bạn ?",
            "Đồng ý kết bạn",
            "Từ chối kết bạn",
            IconType.WARNING
        )
        isShowDialog.value = true
    }

    fun decideFriend(idUser: String, choice: String) {
        val apiClient: APIClient = APIClient(context)
        val apiService = apiClient.client()?.create(ApiInterface::class.java)
        val call = apiService?.performFriend("Bearer ${appSharedPreference.accessToken}", idUser, choice)
        call?.enqueue(object : Callback<ApiResponse.BaseApiResponse<Unit>> {
            override fun onResponse(
                call: Call<ApiResponse.BaseApiResponse<Unit>>,
                response: Response<ApiResponse.BaseApiResponse<Unit>>
            ) {
                if (response.isSuccessful && choice == "Accept") {
                    infoDialog.value = InfoDialog(
                        fun() {
                            isShowDialog.value = false
                        },
                        fun() {
                            isShowDialog.value = false
                        },
                        "Kết bạn thành công",
                        "Bạn đã kết bạn với người này",
                        "OK",
                        "Hủy",
                        IconType.SUCCESS
                    )
                    isShowDialog.value = true
                } else if (response.isSuccessful && choice == "Reject") {
                    infoDialog.value = InfoDialog(
                        fun() {
                            isShowDialog.value = false
                        },
                        fun() {
                            isShowDialog.value = false
                        },
                        "Từ chối thành công",
                        "Bạn đã từ chối kết bạn với người này",
                        "OK",
                        "Hủy",
                        IconType.SUCCESS
                    )
                    isShowDialog.value = true
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorJsonObject = Gson().fromJson(errorBody, JsonObject::class.java)
                    val message = errorJsonObject.get("message").asString
                    infoDialog.value = InfoDialog(
                        fun() {
                            isShowDialog.value = false

                        },
                        fun() {
                            isShowDialog.value = false

                        },
                        "Kết bạn thất bại",
                        message,
                        "OK",
                        "Hủy",
                        IconType.ERROR
                    )
                    isShowDialog.value = true
                }
            }

            override fun onFailure(call: Call<ApiResponse.BaseApiResponse<Unit>>, t: Throwable) {
                infoDialog.value = InfoDialog(
                    fun() {
                        isShowDialog.value = false

                    },
                    fun() {
                        isShowDialog.value = false

                    },
                    "Kết bạn thất bại",
                    "Đã có lỗi xảy ra, vui lòng thử lại",
                    "OK",
                    "Hủy",
                    IconType.ERROR
                )
                isShowDialog.value = true
            }
        })

    }
}