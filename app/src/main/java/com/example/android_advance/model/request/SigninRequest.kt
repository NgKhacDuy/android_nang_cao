package com.example.android_advance.model.request

import com.google.gson.annotations.SerializedName

class SigninRequest {
    @SerializedName("phoneNumber")
    var phoneNumber: String? = null

    @SerializedName("password")
    var password: String? = null

    constructor(phoneNumber: String?, password: String?) {
        this.phoneNumber = phoneNumber
        this.password = password
    }
}