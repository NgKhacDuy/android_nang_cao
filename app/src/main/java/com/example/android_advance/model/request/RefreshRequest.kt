package com.example.android_advance.model.request

import com.google.gson.annotations.SerializedName

class RefreshRequest {
    @SerializedName("refreshToken")
    var refreshToken: String? = null

    constructor(refreshToken: String?) {
        this.refreshToken = refreshToken
    }
}