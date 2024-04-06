package com.example.android_advance.ui.Screen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
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
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel()
class SettingScreenViewModel @Inject constructor(@ApplicationContext private val context: Context) : ViewModel() {
    private val appSharedPreference = AppSharedPreference(context)
    private lateinit var db: DatabaseHelper
    private val _onNewUserInfo = MutableLiveData<UserDto?>()
    val onNewUserInfo: LiveData<UserDto?> get() = _onNewUserInfo
    private val _onIsSignOutSuccess = MutableLiveData<Boolean>()
    val onIsSignOutSuccess: LiveData<Boolean> get() = _onIsSignOutSuccess

    init {
        db = DatabaseHelper(context)
        getUserInfo()
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
                    val userDtoData = response.body()?.data
                    if (userDtoData != null) {
                        _onNewUserInfo.postValue(userDtoData)
                    }
                } else {
                    _onNewUserInfo.postValue(null)
                    Log.e("USER INFO", "error")
                }
            }

            override fun onFailure(call: Call<ApiResponse.BaseApiResponse<UserDto>>, t: Throwable) {
                Log.e("USER INFO ERROR", t.message.toString())
            }
        })
    }

    fun deleteToken() {
        appSharedPreference.refreshToken = ""
        appSharedPreference.accessToken = ""
    }

    fun deleteSqlite() {
        db.deleteUser()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun signOut(): Boolean {
        var isSignOutSuccess = true
        GlobalScope.launch(Dispatchers.IO) {
            launch {
                isSignOutSuccess = signOutAsync()
            }
        }
        return isSignOutSuccess
    }

    suspend fun signOutAsync(): Boolean {
        var isSignOutSuccess = false
        val apiClient: APIClient = APIClient(context)
        val apiService = apiClient.client()?.create(ApiInterface::class.java)
        try {
            val response = apiService?.signOut("Bearer ${appSharedPreference.accessToken}")?.execute()
            if (response?.isSuccessful == true) {
                isSignOutSuccess = true
            } else {
                Log.e("SIGN OUT", "error")
            }
        } catch (e: Exception) {
            Log.e("SIGN OUT ERROR", e.message.toString())
        }
        return isSignOutSuccess
    }
}