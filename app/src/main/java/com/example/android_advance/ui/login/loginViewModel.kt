package com.example.android_advance.ui.login

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.dsc.form_builder.FormState
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators
import com.example.android_advance.api.APIClient
import com.example.android_advance.api.ApiInterface
import com.example.android_advance.api.ApiResponse
import com.example.android_advance.data_class.InfoDialog
import com.example.android_advance.model.request.SigninRequest
import com.example.android_advance.model.response.SigninResponse
import com.example.android_advance.navigation.Route
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
import com.onesignal.OneSignal

@HiltViewModel()
class loginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {
    val isLoading = mutableStateOf(false)
    val infoDialog = mutableStateOf(InfoDialog(fun() {}, fun() {}, "", "", "", ""))
    val isShowDialog = mutableStateOf(false)
    val formState = FormState(
        fields = listOf(
            TextFieldState(
                name = "sdt",
                validators = listOf(
                    Validators.Required()
                )
            ),
            TextFieldState(
                name = "password",
                validators = listOf(
                    Validators.Required()
                )
            ),
        )
    )

    val appSharedPreference = AppSharedPreference(context)

    fun signIn(phoneNumber: String, password: String, navController: NavController) {
        isLoading.value = true
        val oneSignalUserID: String? = OneSignal.User.pushSubscription.id
        val apiClient: APIClient = APIClient(context)
        val signInRequest = SigninRequest(phoneNumber, password, oneSignalUserID)
        val apiService = apiClient.client()?.create(ApiInterface::class.java)
        val call = apiService?.signIn(signInRequest)
        call?.enqueue(object : Callback<ApiResponse.BaseApiResponse<SigninResponse>> {
            override fun onResponse(
                call: Call<ApiResponse.BaseApiResponse<SigninResponse>>,
                response: Response<ApiResponse.BaseApiResponse<SigninResponse>>
            ) {
                if (response.isSuccessful) {
                    isLoading.value = false
                    appSharedPreference.accessToken = response.body()?.data?.accessToken.toString()
                    appSharedPreference.refreshToken =
                        response.body()?.data?.refreshToken.toString()

                    navController.navigate(route = "home") {
                        popUpTo("auth") {
                            inclusive = true
                        }
                    }


                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorJsonObject = Gson().fromJson(errorBody, JsonObject::class.java)
                    val message = errorJsonObject.get("message").asString
                    isLoading.value = false
                    infoDialog.value = InfoDialog(
                        fun() {
                            isShowDialog.value = false

                        },
                        fun() {
                            isShowDialog.value = false

                        },
                        "Đăng nhập thất bại",
                        message,
                        "OK",
                        "Hủy",
                        IconType.ERROR
                    )
                    isShowDialog.value = true


                }
            }

            override fun onFailure(
                call: Call<ApiResponse.BaseApiResponse<SigninResponse>>,
                t: Throwable
            ) {
                Log.e("Signup", "Error: $t")
                isLoading.value = false
            }

        })
    }
}