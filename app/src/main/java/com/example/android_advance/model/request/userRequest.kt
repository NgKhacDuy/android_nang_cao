package com.example.android_advance.model.request

import com.google.gson.annotations.SerializedName

class userRequest(
    @SerializedName("name") var name: String?,
    @SerializedName("password") var password: String?,
    @SerializedName("phoneNumber") var phoneNumber: String?
) {

}