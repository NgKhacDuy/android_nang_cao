package com.example.android_advance.model.response

import com.google.gson.annotations.SerializedName

class roomDto(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("listUsers") var listUsers: ArrayList<String> = arrayListOf(),
    @SerializedName("messages") var messages: ArrayList<messageDto> = arrayListOf(),
    @SerializedName("isGroup") var isGroup: Boolean? = null,
    @SerializedName("lastMessage") var lastMessage: messageDto? = messageDto()
) {


}