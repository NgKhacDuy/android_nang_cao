package com.example.android_advance.model.request

import com.google.gson.annotations.SerializedName

class updateUserInfoRequest(
    @SerializedName("name") var name:String,
    @SerializedName("phoneNumber") var phoneNumber: String
) {
}