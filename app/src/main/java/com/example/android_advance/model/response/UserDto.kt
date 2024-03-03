package com.example.android_advance.model.response

import com.google.gson.annotations.SerializedName

class UserDto(
    @SerializedName("id") var id: String?,
    @SerializedName("name") var name: String?,
    @SerializedName("phoneNumber") var phoneNumber: String?,
    @SerializedName("createAt") var createAt: String?,
    @SerializedName("updateAt") var updateAt: String?,
    @SerializedName("deletedAt") var deletedAt: String?,
    @SerializedName("friends") var friends: ArrayList<String>
) {

}