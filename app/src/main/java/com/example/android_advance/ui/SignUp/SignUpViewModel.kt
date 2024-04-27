package com.example.android_advance.ui.SignUp

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
import com.example.android_advance.model.request.SignupRequest
import com.example.android_advance.redux.Store
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
class SignUpViewModel @Inject constructor(@ApplicationContext private val context: Context) :
    ViewModel() {
    val isLoading = mutableStateOf(false)
    val infoDialog = mutableStateOf(InfoDialog(fun() {}, fun() {}, "", "", "", ""))
    val isShowDialog = mutableStateOf(false)
    val store = Store.getStore()
    val formState = FormState(
        fields = listOf(
            TextFieldState(
                name = "sdt",
                validators = listOf(
                    Validators.Min(10, "Số điện thoại phải tối thiểu 10 số")
                )
            ),
            TextFieldState(
                name = "name",
                validators = listOf(
                    Validators.Required()
                )
            ), TextFieldState(
                name = "password",
                validators = listOf(
                    Validators.Required()
                )
            ),
            TextFieldState(
                name = "password_confirm",
                validators = listOf(
                    Validators.Custom(
                        "Mật khẩu xác nhận không khớp"
                    ) { confirmation -> passwordMatch(confirmation) },
                    Validators.Required("Không được để trống mật khẩu xác nhận")
                )
            )
        )
    )

    private fun passwordMatch(confirmationPass: Any): Boolean {
        return confirmationPass == formState.fields.find { it.name == "password" }?.value.toString()
    }

    private fun navigateToLogin(navController: NavController) {
        navController.navigate(route = "home") {
            popUpTo("auth") {
                inclusive = true
            }
        }
    }

    fun signUp(
        name: String,
        phoneNumber: String,
        password: String,
        callback: (Boolean) -> Unit
    ) {
        isLoading.value = true
        val apiClient: APIClient = APIClient(context)
        val userRequest = SignupRequest(name, password, phoneNumber)
        val apiService = apiClient.client()?.create(ApiInterface::class.java)
        val call = apiService?.signUp(userRequest)
        call?.enqueue(object : Callback<ApiResponse.BaseApiResponse<Unit>> {
            override fun onResponse(
                call: Call<ApiResponse.BaseApiResponse<Unit>>,
                response: Response<ApiResponse.BaseApiResponse<Unit>>
            ) {
                if (response.isSuccessful) {
                    callback(true)

                } else {
                    callback(false)
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
                        "Đăng ký thất bại",
                        message,
                        "OK",
                        "Hủy",
                        IconType.ERROR
                    )
                    isShowDialog.value = true


                }
            }

            override fun onFailure(call: Call<ApiResponse.BaseApiResponse<Unit>>, t: Throwable) {
                Log.e("Signup", "Error: $t")
                isLoading.value = false

            }

        })
    }

    fun generateOtp(
        phoneNumber: String,
        callback: (String) -> Unit
    ) {
        val apiClient: APIClient = APIClient(context)
        val apiService = apiClient.client()?.create(ApiInterface::class.java)
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

    fun checkPhoneNumberExistence2(
        phoneNumber: String,
        callback: (String) -> Unit
    ) {
        val apiClient: APIClient = APIClient(context)
        val apiService = apiClient.client()?.create(ApiInterface::class.java)
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

}

