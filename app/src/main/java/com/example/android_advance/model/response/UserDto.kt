package com.example.android_advance.model.response

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("phoneNumber") var phoneNumber: String? = null,
    @SerializedName("createAt") var createAt: String? = null,
    @SerializedName("updateAt") var updateAt: String? = null,
    @SerializedName("deletedAt") var deletedAt: String? = null,
    @SerializedName("friends") var friends: ArrayList<FriendsDto>? = arrayListOf(),
    @SerializedName("avatar") var avatar: String? = ""
) {

}