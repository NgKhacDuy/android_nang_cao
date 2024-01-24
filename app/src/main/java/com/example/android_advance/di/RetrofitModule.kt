package com.example.android_advance.di

import com.example.android_advance.data.remote.Api
import com.example.android_advance.data.remote.repository.ProductRepositoryImpl
import com.example.android_advance.repo.ProductRepository
import com.example.android_advance.utils.common.Constant
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun provideRetroFit(): Retrofit {
        val gson = GsonBuilder().create()
        return Retrofit.Builder().baseUrl(Constant.BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Singleton
    @Provides
    fun provideProductApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Singleton
    @Provides
    fun provideProductRepository(api: Api): ProductRepository {
        return ProductRepositoryImpl(api)
    }
}