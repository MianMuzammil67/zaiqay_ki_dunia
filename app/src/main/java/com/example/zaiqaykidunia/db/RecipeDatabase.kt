package com.example.zaiqaykidunia.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.zaiqaykidunia.network.Recipe

@Database(entities = [Recipe::class], version = 1)
@TypeConverters(RoomTypeConverters::class)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun getRecipeDao() : RecipeDao
}