package com.example.android_advance.model.response

import com.google.gson.annotations.SerializedName

class FriendResponse(
    @SerializedName("user") var user   : UserDto?   = UserDto(),
    @SerializedName("status") var status : String? = null
)
{

}