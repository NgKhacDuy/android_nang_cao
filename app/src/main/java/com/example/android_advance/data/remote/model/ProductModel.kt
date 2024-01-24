package com.example.android_advance.data.remote.model

import com.example.android_advance.domain.entity.ProductEntity

data class ProductModel(
    val name: String,
    val img: Array<String>,
    val money: String,
)

data class ProductResult(
    val data: List<ProductModel>
)

fun ProductModel.toProductEntity(): ProductEntity {
    return ProductEntity(name, img, money)
}