package com.example.android_advance.model.request

import com.google.gson.annotations.SerializedName

class MessageRequest(
    @SerializedName("senderId") var senderId: String,
    @SerializedName("content") var content: String,
    @SerializedName("roomId") var roomId: String
) {

}