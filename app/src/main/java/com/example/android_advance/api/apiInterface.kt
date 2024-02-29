package com.example.android_advance.api

import com.example.android_advance.model.request.SigninRequest
import com.example.android_advance.model.request.SignupRequest
import com.example.android_advance.model.response.SigninResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST(apiConstant.userSignUpApi)
    fun signUp(@Body() signUpBody: SignupRequest): Call<ApiResponse.BaseApiResponse<Unit>>

    @POST(apiConstant.userSignInApi)
    fun signIn(@Body() signUpBody: SigninRequest): Call<ApiResponse.BaseApiResponse<SigninResponse>>

}