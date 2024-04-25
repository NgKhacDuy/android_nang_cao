package com.example.android_advance.model.request

import com.google.gson.annotations.SerializedName

class RemoveUserOutOfGroup(
    @SerializedName("idRoom") val idRoom: String,
    @SerializedName("idUser") val idUser: String
) {
}