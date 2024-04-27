package com.example.android_advance.model.request

import com.google.gson.annotations.SerializedName

class userRequest(
    @SerializedName("name") var name: String? = null,
    @SerializedName("password") var password: String? = null,
    @SerializedName("phoneNumber") var phoneNumber: String? = null
) {

}