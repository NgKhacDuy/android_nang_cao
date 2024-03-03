package com.example.android_advance.model.request

import com.google.gson.annotations.SerializedName

class SigninRequest(
    @SerializedName("phoneNumber") var phoneNumber: String?,
    @SerializedName("password") var password: String?
) {

}