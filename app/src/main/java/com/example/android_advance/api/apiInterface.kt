package com.example.android_advance.api

import com.example.android_advance.model.request.userRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("user/signup")
    fun signUp(@Body() signUpBody: userRequest): Call<ApiResponse.BaseApiResponse<Unit>>

}