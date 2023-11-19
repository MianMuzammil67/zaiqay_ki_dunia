package com.example.zaiqaykidunia.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.zaiqaykidunia.network.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRecipes(recipe:List<Recipe>)
    @Update
    suspend fun upsertFavourite(recipe: Recipe)
    @Delete
    suspend fun deleteRecipe(recipe: Recipe)
    @Query("SELECT * FROM FoodRecipes_db")
    fun getLocalRecipes(): Flow<List<Recipe>>
    @Query("SELECT * FROM FoodRecipes_db WHERE favourite = 1")
    fun getFavouriteRecipes(): Flow<List<Recipe>>

}