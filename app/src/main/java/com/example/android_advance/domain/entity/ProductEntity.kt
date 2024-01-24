package com.example.android_advance.domain.entity

import com.example.android_advance.data.remote.model.ProductModel

data class ProductEntity(
    val name: String,
    val img: Array<String>,
    val money: String,
)

fun ProductModel.toProductEntity() = ProductEntity(name, img, money)
