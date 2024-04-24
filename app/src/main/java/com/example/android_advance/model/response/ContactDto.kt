package com.example.android_advance.model.response

import com.google.gson.annotations.SerializedName

class ContactDto(
    @SerializedName("users") var users: List<UserDto> = listOf(),
    @SerializedName("favorite") var favorite: List<UserDto> = listOf()
) {
}