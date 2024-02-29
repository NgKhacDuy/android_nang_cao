package com.example.android_advance.api

public class ApiResponse {
    data class BaseApiResponse<T>(
        val code: Int,
        val message: String?,
        val data: T?
    )
}
