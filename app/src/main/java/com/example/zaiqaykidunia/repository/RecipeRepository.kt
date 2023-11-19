package com.example.zaiqaykidunia.repository

import android.content.Context
import com.example.zaiqaykidunia.db.RecipeDatabase
import com.example.zaiqaykidunia.network.Recipe
import com.example.zaiqaykidunia.network.RecipeApiResponse
import com.example.zaiqaykidunia.network.api.RecipeApi
import com.example.zaiqaykidunia.utils.Constants
import com.example.zaiqaykidunia.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import org.json.JSONObject
import javax.inject.Inject

class RecipeRepository @Inject constructor(
    private val api: RecipeApi,
    private val db: RecipeDatabase,
    private val context: Context
) {

    private val _recipeFlow = MutableStateFlow<Resource<RecipeApiResponse>>(Resource.Loading())
    private val _searchFlow = MutableStateFlow<Resource<RecipeApiResponse>>(Resource.Loading())

    val recipeFlow: StateFlow<Resource<RecipeApiResponse>>
        get() {
            return _recipeFlow
        }
    val searchFlow: StateFlow<Resource<RecipeApiResponse>>
        get() {
            return _searchFlow
        }

    suspend fun getRecipes(tag: String, number: Int) {
        if (Constants.isNetworkAvailable(context)) {
            fetchRecipesFromApi(tag, number)
        } else {
            fetchRecipesFromDatabase()
        }
    }
    private suspend fun fetchRecipesFromApi(tag: String, number: Int) {
        val result = api.getRecipes(tag, number)
        if (result.isSuccessful && result.body() != null) {
            db.getRecipeDao().upsertRecipes(result.body()!!.recipes)
            _recipeFlow.emit(Resource.Success(result.body()))
        } else if (result.errorBody() != null) {
            val errorObj = JSONObject(result.errorBody()!!.charStream().readText())
            _recipeFlow.emit(Resource.Error(errorObj.getString("Empty response body")))
        }
    }
    private suspend fun fetchRecipesFromDatabase() {
        val recipes = db.getRecipeDao().getLocalRecipes()
        recipes.collect {
            _recipeFlow.emit(Resource.Success(RecipeApiResponse(it)))
        }
    }
    suspend fun toggleFavBtn(recipe: Recipe){
        val savedRecipes = db.getRecipeDao().getLocalRecipes().first()
        val savedRecipe = savedRecipes.find { it.id == recipe.id}
        savedRecipe?.let {
            it.favourite= !it.favourite
            db.getRecipeDao().upsertFavourite(it)
        }
    }

/**
    //    suspend fun getRecipes(tag: String, number: Int) {
//        val result = api.getRecipies(tag, number)
//        if (result.isSuccessful && result.body() != null) {
//            _recipeFlow.emit(Resource.Success(result.body()))
//        } else if (result.errorBody() != null) {
//            val errorObj = JSONObject(result.errorBody()!!.charStream().readText())
//            _recipeFlow.emit(Resource.Error(errorObj.getString("Empty response body")))
//        }
//    }**/
    suspend fun searchRecipes(query: String, number: Int) {
        val result = api.searchRecipes(query, number)
        if (result.isSuccessful && result.body() != null) {
            _searchFlow.emit(Resource.Success(result.body()))
        } else if (result.errorBody() != null) {
            val errorObj = JSONObject(result.errorBody()!!.charStream().readText())
            _searchFlow.emit(Resource.Error(errorObj.getString("Empty response body")))
        }
    }

    suspend fun saveRecipes(recipe: List<Recipe>) = db.getRecipeDao().upsertRecipes(recipe)
    suspend fun updateFavorite(recipe: Recipe) = db.getRecipeDao().upsertFavourite(recipe)
    suspend fun deleteRecipe(recipe: Recipe) = db.getRecipeDao().deleteRecipe(recipe)
    fun getLocalRecipes() = db.getRecipeDao().getLocalRecipes()
    fun getFavoriteRecipes() = db.getRecipeDao().getFavouriteRecipes()
}