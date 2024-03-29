package com.example.android_advance.ui.Screen

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_advance.api.APIClient
import com.example.android_advance.api.ApiInterface
import com.example.android_advance.api.ApiResponse
import com.example.android_advance.model.response.FriendResponse
import com.example.android_advance.shared_preference.AppSharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel()
class ProfileScreenViewModel @Inject constructor(@ApplicationContext private val context: Context) : ViewModel() {
    private val appSharedPreference = AppSharedPreference(context)
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
//                    if (userDtoData != null) {
//                        if(userDtoData.toString().isNotEmpty()){
//                            val listType = object : TypeToken<List<FriendList>>() {}.type
//                            Log.e("FRIEND INFO", response.body()!!.data.toString())
//                            val temp: List<FriendList> = gson.fromJson(userDtoData.toString(), listType)
//                            liveData.postValue(temp)
//                            Log.e("FRIEND INFO", temp.toString())
//                        }
//                    }
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
}