package com.example.android_advance.domain.usecases

import com.example.android_advance.domain.entity.ProductEntity
import com.example.android_advance.domain.entity.toProductEntity
import com.example.android_advance.repo.ProductRepository
import com.example.android_advance.utils.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetProductUseCase @Inject constructor(private val productRepository: ProductRepository) {
    operator fun invoke(): Flow<Resource<List<ProductEntity>>> = flow {
        try {
            emit(Resource.Loading<List<ProductEntity>>())
            val product = productRepository.getProduct().map { it.toProductEntity() }
            emit(Resource.Success<List<ProductEntity>>(product))
        } catch (e: HttpException) {
            emit(
                Resource.Error<List<ProductEntity>>(
                    e.localizedMessage ?: "An unexpected error occured"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error<List<ProductEntity>>("Couldn't reach server. Check your internet connection."))
        }
    }
}