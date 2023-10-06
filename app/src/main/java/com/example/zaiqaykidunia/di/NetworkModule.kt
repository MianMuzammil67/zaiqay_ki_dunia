package com.example.zaiqaykidunia.di

import com.example.zaiqaykidunia.network.api.RecipeApi
import com.example.zaiqaykidunia.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    @Singleton
    @Provides
    fun provideApiResponse(retrofit: Retrofit):RecipeApi{
        return retrofit.create(RecipeApi::class.java)
    }
}