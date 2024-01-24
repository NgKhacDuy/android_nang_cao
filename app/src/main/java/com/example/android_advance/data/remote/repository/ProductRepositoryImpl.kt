package com.example.android_advance.data.remote.repository

import com.example.android_advance.data.remote.Api
import com.example.android_advance.data.remote.model.ProductModel
import com.example.android_advance.repo.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val api: Api) : ProductRepository {
    override suspend fun getProduct(): List<ProductModel> {
        return api.getProducts().body()!!.data
    }

}