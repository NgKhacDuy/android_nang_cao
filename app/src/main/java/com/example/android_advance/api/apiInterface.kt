package com.example.android_advance.api

import com.example.android_advance.model.request.FriendRequest
import com.example.android_advance.model.request.RefreshRequest
import com.example.android_advance.model.request.SigninRequest
import com.example.android_advance.model.request.SignupRequest
import com.example.android_advance.model.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @POST(apiConstant.userSignUpApi)
    fun signUp(@Body() signUpBody: SignupRequest): Call<ApiResponse.BaseApiResponse<Unit>>

    @POST(apiConstant.userSignInApi)
    fun signIn(@Body() signUpBody: SigninRequest): Call<ApiResponse.BaseApiResponse<SigninResponse>>

    @POST(apiConstant.userRefresh)
    fun refresh(@Body() refreshBody: RefreshRequest): Call<ApiResponse.BaseApiResponse<SigninResponse>>

    @GET(apiConstant.userInfo)
    fun profile(@Header("Authorization") authHeader: String): Call<ApiResponse.BaseApiResponse<UserDto>>

    @GET(apiConstant.userSearch + "/{keyword}")
    fun Search(
        @Header("Authorization") authHeader: String,
        @Path("keyword") keyword: String
    ): Call<ApiResponse.BaseApiResponse<SearchDto>>

    @GET(apiConstant.userFriend)
    fun friend(@Header("Authorization") authHeader: String): Call<ApiResponse.BaseApiResponse<List<FriendResponse>>>

    @POST(apiConstant.userFriend)
    fun addFriend(
        @Header("Authorization") authHeader: String,
        @Body() friendBody: FriendRequest
    ): Call<ApiResponse.BaseApiResponse<Unit>>

    @GET(apiConstant.userSignOut)
    fun signOut(
        @Header("Authorization") authHeader: String,
    ): Call<ApiResponse.BaseApiResponse<Unit>>

    @PATCH(apiConstant.userFriend + "/{id}")
    fun performFriend(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String,
        @Query("status") status: String
    ): Call<ApiResponse.BaseApiResponse<Unit>>

    @GET(apiConstant.agora + "/{name}")
    fun getAgoraToken(
        @Path("name") name: String
    ): Call<ApiResponse.BaseApiResponse<AgoraTokenDto>>
}