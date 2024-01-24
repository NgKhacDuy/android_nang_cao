package com.example.android_advance.repo

import com.example.android_advance.data.remote.model.ProductModel

interface ProductRepository {
    suspend fun getProduct(): List<ProductModel>
}