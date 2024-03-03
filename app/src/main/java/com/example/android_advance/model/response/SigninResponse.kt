package com.example.android_advance.model.response

import com.google.gson.annotations.SerializedName

class SigninResponse(
    @SerializedName("accessToken") var accessToken: String?,
    @SerializedName("refreshToken") var refreshToken: String?
) {

}