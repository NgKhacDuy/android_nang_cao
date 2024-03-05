package com.example.android_advance.model.request

import com.google.gson.annotations.SerializedName

class SearchRequest (
    @SerializedName("keyword") var keyword: String?
){

}