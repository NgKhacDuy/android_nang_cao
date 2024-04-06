package com.example.android_advance.model.response

import com.google.gson.annotations.SerializedName

class ImageDto(
    @SerializedName("id") var id: String? = "",
    @SerializedName("url") var url: String? = ""
) {
}