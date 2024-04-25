package com.example.android_advance.model.request

import com.google.gson.annotations.SerializedName

class AddUserToGroupRequest(
    @SerializedName("idRoom") val idRoom: String,
    @SerializedName("idUser") val idUser: ArrayList<String>
) {
}