package com.example.zaiqaykidunia.repository

import com.example.zaiqaykidunia.network.RecipeApiResponse
import com.example.zaiqaykidunia.network.api.RecipeApi
import com.example.zaiqaykidunia.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject
import javax.inject.Inject

class RecipeRepository @Inject constructor(private val api: RecipeApi) {

    private val _recipeFlow = MutableStateFlow<Resource<RecipeApiResponse>>(Resource.Loading())
    val recipeFlow: StateFlow<Resource<RecipeApiResponse>>
        get() {
            return _recipeFlow
        }
    suspend fun getRecipes(tag: String, number: Int) {
        val result = api.getRecipies(tag, number)
        if (result.isSuccessful && result.body() != null) {
            _recipeFlow.emit(Resource.Success(result.body()))
        } else if (result.errorBody() != null) {
            val errorObj = JSONObject(result.errorBody()!!.charStream().readText())
                    _recipeFlow.emit(Resource.Error(errorObj.getString("Message")))
        }
    }

}