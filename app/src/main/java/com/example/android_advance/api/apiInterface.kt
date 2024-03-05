package com.example.android_advance.api

import com.example.android_advance.model.request.RefreshRequest
import com.example.android_advance.model.request.SigninRequest
import com.example.android_advance.model.request.SignupRequest
import com.example.android_advance.model.response.SigninResponse
import com.example.android_advance.model.response.UserDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {
    @POST(apiConstant.userSignUpApi)
    fun signUp(@Body() signUpBody: SignupRequest): Call<ApiResponse.BaseApiResponse<Unit>>

    @POST(apiConstant.userSignInApi)
    fun signIn(@Body() signUpBody: SigninRequest): Call<ApiResponse.BaseApiResponse<SigninResponse>>

    @POST(apiConstant.userRefresh)
    fun refresh(@Body() refreshBody: RefreshRequest): Call<ApiResponse.BaseApiResponse<SigninResponse>>

    @GET(apiConstant.userInfo)
    fun profile(@Header("Authorization") authHeader: String): Call<ApiResponse.BaseApiResponse<UserDto>>

    @GET(apiConstant.userSearch+"/{keyword}")
    fun Search(@Path("keyword") keyword:String): Call<ApiResponse.BaseApiResponse<UserDto>>
}