package com.example.android_advance.model.request

import com.google.gson.annotations.SerializedName

class SignupRequest {
    @SerializedName("name")
    var name: String? = null

    @SerializedName("password")
    var password: String? = null

    @SerializedName("phoneNumber")
    var phoneNumber: String? = null

    constructor(name: String?, password: String?, phoneNumber: String?) {
        this.name = name
        this.password = password
        this.phoneNumber = phoneNumber
    }
}