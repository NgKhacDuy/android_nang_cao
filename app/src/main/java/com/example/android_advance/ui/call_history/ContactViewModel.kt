package com.example.android_advance.ui.call_history

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.android_advance.api.APIClient
import com.example.android_advance.api.ApiInterface
import com.example.android_advance.api.ApiResponse
import com.example.android_advance.model.response.ContactDto
import com.example.android_advance.model.response.UserDto
import com.example.android_advance.model.response.roomDto
import com.example.android_advance.navigation.Route
import com.example.android_advance.redux.AddRoomDto
import com.example.android_advance.redux.Store
import com.example.android_advance.shared_preference.AppSharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {
    private val appSharedPreference = AppSharedPreference(context)

    private val _onNewFavorite = MutableLiveData<List<UserDto>>()
    private val _onNewContact = MutableLiveData<List<UserDto>>()
    val onNewFavorite: LiveData<List<UserDto>> get() = _onNewFavorite
    val onNewContact: LiveData<List<UserDto>> get() = _onNewContact
    val store = Store.getStore()


    init {
        getListContact()
    }

    private fun getListContact() {
        try {
            val apiClient: APIClient = APIClient(context)
            val apiService = apiClient.client()?.create(ApiInterface::class.java)
            val call = apiService?.getContact("Bearer ${appSharedPreference.accessToken}")
            call?.enqueue(object : Callback<ApiResponse.BaseApiResponse<ContactDto>> {
                override fun onResponse(
                    call: Call<ApiResponse.BaseApiResponse<ContactDto>>,
                    response: Response<ApiResponse.BaseApiResponse<ContactDto>>
                ) {
                    if (response.isSuccessful) {
                        val contactDto = response.body()?.data
                        if (contactDto != null) {
                            _onNewFavorite.postValue(contactDto.favorite)
                            _onNewContact.postValue(contactDto.users)
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ApiResponse.BaseApiResponse<ContactDto>>,
                    t: Throwable
                ) {
                    Log.e("ContactViewModel", "onFailure: ${t.message}")
                }

            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun navigateToRoom(idPartner: String, navController: NavController) {
        try {
            val apiClient: APIClient = APIClient(context)
            val apiService = apiClient.client()?.create(ApiInterface::class.java)
            val call =
                apiService?.getRoom(
                    authHeader = "Bearer ${appSharedPreference.accessToken}",
                    idUser = idPartner
                )
            call?.enqueue(object : Callback<ApiResponse.BaseApiResponse<roomDto>> {
                override fun onResponse(
                    call: Call<ApiResponse.BaseApiResponse<roomDto>>,
                    response: Response<ApiResponse.BaseApiResponse<roomDto>>
                ) {
                    if (response.isSuccessful) {
                        val roomDto = response.body()?.data
                        if (roomDto != null) {
                            store!!.dispatch(AddRoomDto(roomDto))
                            navController.navigate(
                                Route.MessageScreen.route
                            )
                        }
                    }
                }

                override fun onFailure(
                    call: Call<ApiResponse.BaseApiResponse<roomDto>>,
                    t: Throwable
                ) {
                    Log.e("ContactViewModel", "onFailure: ${t.message}")
                }

            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}