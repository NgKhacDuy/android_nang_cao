package com.example.android_advance.ui.Screen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.android_advance.api.APIClient
import com.example.android_advance.api.ApiInterface
import com.example.android_advance.api.ApiResponse
import com.example.android_advance.database.DatabaseHelper
import com.example.android_advance.model.request.SigninRequest
import com.example.android_advance.model.response.SigninResponse
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.navigation.Route
import com.example.android_advance.shared_preference.AppSharedPreference
import com.example.android_advance.ui.Home.HomeScreenViewModel
import com.example.android_advance.ui.login.LoginScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel()
class SettingScreenViewModel @Inject constructor(@ApplicationContext private val context: Context) : ViewModel(){
    private val appSharedPreference = AppSharedPreference(context)
    private lateinit var db: DatabaseHelper
    init {
        db = DatabaseHelper(context)
    }
    fun getUserInfo(): MutableLiveData<UserDto?> {
        val apiClient: APIClient = APIClient(context)
        val apiService = apiClient.client()?.create(ApiInterface::class.java)
        val liveData = MutableLiveData<UserDto?>()
        val call = apiService?.profile("Bearer ${appSharedPreference.accessToken}")
        call?.enqueue(object : Callback<ApiResponse.BaseApiResponse<UserDto>> {
            override fun onResponse(
                call: Call<ApiResponse.BaseApiResponse<UserDto>>,
                response: Response<ApiResponse.BaseApiResponse<UserDto>>
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

            override fun onFailure(call: Call<ApiResponse.BaseApiResponse<UserDto>>, t: Throwable) {
                Log.e("USER INFO ERROR", t.message.toString())
            }
        })
        return liveData
    }

    fun deleteToken()
    {
        appSharedPreference.refreshToken=""
        appSharedPreference.accessToken=""
    }

    fun deleteSqlite(){
        db.deleteUser()
    }

    fun signOut(): Boolean {
        var isSignOutSuccess = false
        val apiClient: APIClient = APIClient(context)
        val apiService = apiClient.client()?.create(ApiInterface::class.java)
        val call = apiService?.signOut("Bearer ${appSharedPreference.accessToken}")
        call?.enqueue(object : Callback<ApiResponse.BaseApiResponse<Unit>> {
            override fun onResponse(
                p0: Call<ApiResponse.BaseApiResponse<Unit>>,
                p1: Response<ApiResponse.BaseApiResponse<Unit>>
            ) {
                isSignOutSuccess = p1.isSuccessful
            }

            override fun onFailure(p0: Call<ApiResponse.BaseApiResponse<Unit>>, p1: Throwable) {
                TODO("Not yet implemented")
            }

        })
        return isSignOutSuccess
    }
}