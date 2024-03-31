package com.example.android_advance.model.request

import com.google.gson.annotations.SerializedName

class RoomRequest {
    @SerializedName("listUser")
    var listUser: ArrayList<String>

    @SerializedName("name")
    var groupName: String

    constructor(listUser: ArrayList<String>, groupName: String) {
        this.listUser = listUser
        this.groupName = groupName
    }
}