package com.example.android_advance.model.request

import com.google.gson.annotations.SerializedName

class RoomRequest {
    @SerializedName("roomId")
    var roomId: String

    constructor(roomId: String) {
        this.roomId = roomId
    }
}