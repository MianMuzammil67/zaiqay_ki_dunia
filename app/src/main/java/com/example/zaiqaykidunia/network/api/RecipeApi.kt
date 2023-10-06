package com.example.zaiqaykidunia.network.api

import com.example.zaiqaykidunia.network.RecipeApiResponse
import com.example.zaiqaykidunia.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    @GET("recipes/random")
    suspend fun getRecipies(
        @Query("tags")
        tags : String,
        @Query("number")
        number : Int,
        @Query("apiKey")
        apiKey : String = API_KEY
    ):Response<RecipeApiResponse>



}