package com.example.android_advance.data.remote

import com.example.android_advance.data.remote.model.ProductResult
import com.example.android_advance.utils.common.Constant
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET(Constant.PRODUCT_API)
    suspend fun getProducts(): Response<ProductResult>
}