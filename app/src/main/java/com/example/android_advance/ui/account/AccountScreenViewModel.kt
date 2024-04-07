package com.example.android_advance.ui.account

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dsc.form_builder.FormState
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators
import com.example.android_advance.api.APIClient
import com.example.android_advance.api.ApiInterface
import com.example.android_advance.api.ApiResponse
import com.example.android_advance.database.DatabaseHelper
import com.example.android_advance.model.request.PasswordRequest
import com.example.android_advance.model.request.updateUserInfoRequest
import com.example.android_advance.model.request.userRequest
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.shared_preference.AppSharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel()
class AccountScreenViewModel @Inject constructor(@ApplicationContext private val context: Context) : ViewModel() {
    private val appSharedPreference = AppSharedPreference(context)
    private val db: DatabaseHelper = DatabaseHelper(context)
    val passwordFormState = FormState(
        fields = listOf(
            TextFieldState(
                name = "password",
                validators = listOf(
                    Validators.Required()
                )
            ),
            TextFieldState(
                name = "checkpass",
                validators = listOf(
                    Validators.Required()
                )
            ),
        )
    )
    val userInfoFormState = FormState(
        fields = listOf(
            TextFieldState(
                name = "sdt",
                validators = listOf(
                    Validators.Required()
                )
            ),
            TextFieldState(
                name = "name",
                validators = listOf(
                    Validators.Required()
                )
            ),
        )
    )
    val userId = db.getUserId()
    val currentUser = db.getUser().first()
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

    fun updateUserInfo(name: String,phoneNumber: String): String {
        val apiClient: APIClient = APIClient(context)
        val apiService = apiClient.client()?.create(ApiInterface::class.java)
        var message = ""
        val userRequest = updateUserInfoRequest(name,phoneNumber)
        val call = userId?.let {
            apiService?.updateUserInfo("Bearer ${appSharedPreference.accessToken}",
                it,userRequest)
        }
        call?.enqueue(object : Callback<ApiResponse.BaseApiResponse<Unit>> {
            override fun onResponse(
                call: Call<ApiResponse.BaseApiResponse<Unit>>,
                response: Response<ApiResponse.BaseApiResponse<Unit>>
            ) {
                if (response.isSuccessful) {
                    Log.e("USER UPDATE INFO", response.body().toString())
                    val userDtoData = response.body()?.data
                    if (userDtoData != null) {
                        message= userDtoData.toString()
                    }
                } else {
                    Log.e("USER UPDATE INFO", "error")
                }
            }

            override fun onFailure(call: Call<ApiResponse.BaseApiResponse<Unit>>, t: Throwable) {
                Log.e("USER UPDATE INFO ERROR", t.message.toString())
            }
        })
        return message
    }

    fun updatePassword(password: String): String {
        val apiClient: APIClient = APIClient(context)
        val apiService = apiClient.client()?.create(ApiInterface::class.java)
        var message = ""
        val passwordRequest = PasswordRequest(password)
        val call = userId?.let {
            apiService?.changePassword("Bearer ${appSharedPreference.accessToken}",
                it,passwordRequest)
        }
        call?.enqueue(object : Callback<ApiResponse.BaseApiResponse<Unit>> {
            override fun onResponse(
                call: Call<ApiResponse.BaseApiResponse<Unit>>,
                response: Response<ApiResponse.BaseApiResponse<Unit>>
            ) {
                if (response.isSuccessful) {
                    Log.e("PASSWORD CHANGE", response.body().toString())
                    val userDtoData = response.body()?.data
                    if (userDtoData != null) {
                        message= userDtoData.toString()
                    }
                } else {
                    Log.e("PASSWORD CHANGE", "error")
                }
            }

            override fun onFailure(call: Call<ApiResponse.BaseApiResponse<Unit>>, t: Throwable) {
                Log.e("PASSWORD CHANGE ERROR", t.message.toString())
            }
        })
        return message
    }

    fun updateUserSqlite()
    {
        db.deleteUser()
        getUserInfo().value?.let { db.insertUser(it) }
    }
}