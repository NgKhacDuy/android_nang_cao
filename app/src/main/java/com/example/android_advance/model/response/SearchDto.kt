package com.example.android_advance.model.response

import com.google.gson.annotations.SerializedName

class SearchDto(
    @SerializedName("user") var listUserDto: ArrayList<UserDto> = arrayListOf(),
    @SerializedName("room") var listRoomDto: ArrayList<roomDto> = arrayListOf()
) {
}