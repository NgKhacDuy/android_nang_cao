package com.example.android_advance.ui.product

import com.example.android_advance.domain.entity.ProductEntity

data class ProductState(
    val isLoading: Boolean = false,
    val listProduct: List<ProductEntity> = emptyList(),
    val error: String = ""
)