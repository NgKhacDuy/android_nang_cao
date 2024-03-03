package com.example.android_advance.model.response

import com.google.gson.annotations.SerializedName

class UserDto {
    @SerializedName("id")
    var id: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("phoneNumber")
    var phoneNumber: String? = null

    @SerializedName("createAt")
    var createAt: String? = null

    @SerializedName("updateAt")
    var updateAt: String? = null

    @SerializedName("deletedAt")
    var deletedAt: String? = null

    @SerializedName("friends")
    var friends: ArrayList<String> = arrayListOf()

    constructor(
        id: String?,
        name: String?,
        phoneNumber: String?,
        createAt: String?,
        updateAt: String?,
        deletedAt: String?,
        friends: ArrayList<String>
    ) {
        this.id = id
        this.name = name
        this.phoneNumber = phoneNumber
        this.createAt = createAt
        this.updateAt = updateAt
        this.deletedAt = deletedAt
        this.friends = friends
    }
}