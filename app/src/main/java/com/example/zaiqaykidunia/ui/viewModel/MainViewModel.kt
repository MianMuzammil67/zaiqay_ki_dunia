package com.example.zaiqaykidunia.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zaiqaykidunia.network.RecipeApiResponse
import com.example.zaiqaykidunia.repository.RecipeRepository
import com.example.zaiqaykidunia.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(private val repository: RecipeRepository) : ViewModel() {

    val recipeFlow : StateFlow<Resource<RecipeApiResponse>>
        get() {
            return repository.recipeFlow
        }
    fun getRecipes(tag: String, number: Int) {
        viewModelScope.launch {
            repository.getRecipes(tag, number)
        }
    }

}