package com.example.android_advance.ui.forgotPassword

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_advance.api.APIClient
import com.example.android_advance.api.ApiInterface
import com.example.android_advance.api.ApiResponse
import com.example.android_advance.database.DatabaseHelper
import com.example.android_advance.model.request.PasswordRequest
import com.example.android_advance.socketio.SocketManager
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel()
class forgetPasswordViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {
    private var socketManager = SocketManager.getInstance(context)
    var db: DatabaseHelper = DatabaseHelper(context)
    val apiClient: APIClient = APIClient(context)
    val apiService = apiClient.client()?.create(ApiInterface::class.java)
    var temporay=""
    fun checkPhoneNumber(phoneNumber:String):Boolean{
        return db.getUser().isEmpty()
        //return (phoneNumber==db.getUser().first().phoneNumber);
    }
         fun checkPhoneNumberExistence(phoneNumber: String):String {
            var userid=""
            val call= apiService?.isPhoneNumberExist(phoneNumber)
            call?.enqueue(object : Callback<ApiResponse.BaseApiResponse<String>> {
                override fun onResponse(
                    call: Call<ApiResponse.BaseApiResponse<String>>,
                    response: Response<ApiResponse.BaseApiResponse<String>>
                ) {
                    if(response.isSuccessful){
                        userid= response.body()?.data.toString()
                        Log.d("test",userid)
                    }
                    else{
                        Log.d("test","am not feeling so good")
                    }
                }
                override fun onFailure(
                    call: Call<ApiResponse.BaseApiResponse<String>>,
                    t: Throwable
                ) {
                    Log.e("tesst", t.message.toString())
                }
            })

            Log.d("test2",userid)
            return userid

        }
    fun checkPhoneNumberExistence2(
        phoneNumber: String,
        callback: (String) -> Unit
    ) {
        val call = apiService?.isPhoneNumberExist(phoneNumber)

        call?.enqueue(object : Callback<ApiResponse.BaseApiResponse<String>> {
            override fun onResponse(
                call: Call<ApiResponse.BaseApiResponse<String>>,
                response: Response<ApiResponse.BaseApiResponse<String>>
            ) {
                if (response.isSuccessful) {
                    val userId = response.body()?.data.toString()
                    Log.d("test", userId)
                    callback(userId)
                } else {
                    Log.d("test", "am not feeling so good")
                    callback("")
                }
            }

            override fun onFailure(
                call: Call<ApiResponse.BaseApiResponse<String>>,
                t: Throwable
            ) {
                Log.e("tesst", t.message.toString())
                callback("")
            }
        })
    }
    fun resetPassword(newPassword:String,userId:String,callback: (Boolean) -> Unit){
        val passwordRequest= PasswordRequest(newPassword)

        val call = userId.let {
            apiService?.resetPassword(
                it,passwordRequest)
        }
        call?.enqueue(object : Callback<ApiResponse.BaseApiResponse<Unit>> {
            override fun onResponse(
                call: Call<ApiResponse.BaseApiResponse<Unit>>,
                response: Response<ApiResponse.BaseApiResponse<Unit>>
            ) {
                if (response.isSuccessful) {
                    val result=true
                    callback(result)
                } else {
                    val result=false
                    callback(result)
                    Log.d("test", "am not feeling so good")
                }
            }

            override fun onFailure(
                call: Call<ApiResponse.BaseApiResponse<Unit>>,
                t: Throwable
            ) {
                Log.e("error", t.message.toString())
            }
        })
    }
    fun generateOtp(phoneNumber:String,
        callback: (String) -> Unit
    ) {
        val call = apiService?.generateOtpForResetPassword(phoneNumber)

        call?.enqueue(object : Callback<ApiResponse.BaseApiResponse<String>> {
            override fun onResponse(
                call: Call<ApiResponse.BaseApiResponse<String>>,
                response: Response<ApiResponse.BaseApiResponse<String>>
            ) {
                if (response.isSuccessful) {
                    val otpgenCode = response.body()?.data.toString()
                    callback(otpgenCode)
                } else {
                    Log.d("test", "am not feeling so good")
                    callback("")
                }
            }

            override fun onFailure(
                call: Call<ApiResponse.BaseApiResponse<String>>,
                t: Throwable
            ) {
                Log.e("tesst", t.message.toString())
                callback("")
            }
        })
    }


}