package com.example.android_advance.ui.SignUp

import android.util.Log
import androidx.lifecycle.ViewModel
import com.dsc.form_builder.FormState
import com.dsc.form_builder.TextFieldState
import com.dsc.form_builder.Validators
import com.example.android_advance.api.APIClient
import com.example.android_advance.api.ApiInterface
import com.example.android_advance.api.ApiResponse
import com.example.android_advance.model.request.userRequest
import com.google.gson.Gson
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@HiltViewModel
class SignUpViewModel : ViewModel() {
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

    fun signUp(name: String, phoneNumber: String, password: String) {
        val userRequest = userRequest(name, password, phoneNumber)
        val apiService = APIClient.client?.create(ApiInterface::class.java)
        val call = apiService?.signUp(userRequest)
        call?.enqueue(object : Callback<ApiResponse.BaseApiResponse<Unit>> {
            override fun onResponse(
                call: Call<ApiResponse.BaseApiResponse<Unit>>,
                response: Response<ApiResponse.BaseApiResponse<Unit>>
            ) {
                if (response.isSuccessful) {
                    Log.e("signup", "Success")

                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorJsonObject = Gson().fromJson(errorBody, JsonObject::class.java)
                    val message = errorJsonObject.get("message").asString
                    Log.e("Signup", "Error: $message")


                }
            }

            override fun onFailure(call: Call<ApiResponse.BaseApiResponse<Unit>>, t: Throwable) {
                Log.e("Signup", "Error: $t")

            }

        })
    }
}