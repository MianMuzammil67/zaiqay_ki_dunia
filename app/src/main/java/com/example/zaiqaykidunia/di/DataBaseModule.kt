package com.example.zaiqaykidunia.di

import android.content.Context
import androidx.room.Room
import com.example.zaiqaykidunia.db.RecipeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataBaseModule {
    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): RecipeDatabase {
        return Room.databaseBuilder(context, RecipeDatabase::class.java, "FoodRecipes_db")
            .build()
    }
}