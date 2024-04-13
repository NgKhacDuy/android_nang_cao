package com.example.android_advance.model.response

import com.google.gson.annotations.SerializedName

class FriendRequestDto(
    @SerializedName("id") var id: String? = null,
    @SerializedName("idSender") var idSender: String? = null,
    @SerializedName("idReceiver") var idReceiver: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("user") var user: ArrayList<UserDto>? = arrayListOf()
) {
}