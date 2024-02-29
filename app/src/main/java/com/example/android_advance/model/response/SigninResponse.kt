package com.example.android_advance.model.response

import com.google.gson.annotations.SerializedName

class SigninResponse {
    @SerializedName("accessToken")
    var accessToken: String? = null

    @SerializedName("refreshToken")
    var refreshToken: String? = null

    constructor(accessToken: String?, refreshToken: String?) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
    }
}