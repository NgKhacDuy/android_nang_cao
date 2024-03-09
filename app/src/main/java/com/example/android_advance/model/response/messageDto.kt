package com.example.android_advance.model.response

import com.google.gson.annotations.SerializedName

class messageDto(
    @SerializedName("id") var id: String? = null,
    @SerializedName("senderId") var senderId: String? = null,
    @SerializedName("content") var content: String? = null,
    @SerializedName("readBy") var readBy: ArrayList<String> = arrayListOf(),
    @SerializedName("createAt") var createAt: String? = null

) {
}