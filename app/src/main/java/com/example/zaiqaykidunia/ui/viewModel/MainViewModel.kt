package com.example.zaiqaykidunia.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zaiqaykidunia.network.Recipe
import com.example.zaiqaykidunia.network.RecipeApiResponse
import com.example.zaiqaykidunia.repository.RecipeRepository
import com.example.zaiqaykidunia.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: RecipeRepository) : ViewModel() {

//    init {
//        getRecipes(tag = "main course", number = 20)
//    }
    val recipeFlow: StateFlow<Resource<RecipeApiResponse>>
        get() {
            return repository.recipeFlow
        }
    val searchFlow: StateFlow<Resource<RecipeApiResponse>>
        get() {
            return repository.searchFlow
        }

    fun getRecipes(tag: String, number: Int) {
        viewModelScope.launch {
            repository.getRecipes(tag, number)
        }
    }
    fun searchRecipe(query: String, number: Int) {
        viewModelScope.launch {
            repository.searchRecipes(query, number)
        }
    }
    fun toggleFavoriteStatus(recipe: Recipe){
        viewModelScope.launch { repository.toggleFavBtn(recipe) }
    }

    fun saveRecipes(recipe: List<Recipe>) = viewModelScope.launch { repository.saveRecipes(recipe) }
    fun updateFavorite(recipe: Recipe) = viewModelScope.launch { repository.updateFavorite(recipe) }
    fun deleteRecipe(recipe: Recipe) = viewModelScope.launch { repository.deleteRecipe(recipe) }
    fun getLocalRecipes() = repository.getLocalRecipes()
    fun getFavoriteRecipes() = repository.getFavoriteRecipes()

}