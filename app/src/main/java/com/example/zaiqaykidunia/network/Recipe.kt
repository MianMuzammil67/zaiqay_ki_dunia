package com.example.zaiqaykidunia.network

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
@Entity(tableName = "FoodRecipes_db")
@Parcelize
data class Recipe(
    @SerializedName("favourite")
    var favourite :Boolean  = false,
    @SerializedName("aggregateLikes")
    val aggregateLikes: Int,
    @SerializedName("cheap")
    val cheap: Boolean,
    @SerializedName("dairyFree")
    val dairyFree: Boolean,
    @SerializedName("extendedIngredients")
    val extendedIngredients: List<ExtendedIngredient>,
    @SerializedName("analyzedInstructions")
    val analyzedInstructions:  List<AnalyzedInstruction>,
    @SerializedName("glutenFree")
    val glutenFree: Boolean,
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String?,
    @SerializedName("readyInMinutes")
    val readyInMinutes: Int,
    @SerializedName("sourceName")
    val sourceName: String?,
    @SerializedName("sourceUrl")
    val sourceUrl: String,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("vegan")
    val vegan: Boolean,
    @SerializedName("vegetarian")
    val vegetarian: Boolean,
    @SerializedName("servings")
    val servings: Int,
    @SerializedName("healthScore")
    val healthScore: Int,
    @SerializedName("veryHealthy")
    val veryHealthy: Boolean
) : Parcelable



/*
//    val aggregateLikes: Int,
//    val analyzedInstructions: @RawValue List<AnalyzedInstruction>,
//    val cheap: Boolean,
//    val cookingMinutes: Int,
//    val creditsText: String,
//    val cuisines:@RawValue List<Any>,
//    val dairyFree: Boolean,
//    val diets: List<String>,
//    val dishTypes: List<String>,
//    val extendedIngredients:@RawValue List<ExtendedIngredient>,
//    val gaps: String,
//    val glutenFree: Boolean,
//    val healthScore: Int,
//    val id: Int,
//    val image: String,
//    val imageType: String,
//    val instructions: String,
//    val license: String,
//    val lowFodmap: Boolean,
//    val occasions:@RawValue List<Any>,
//    val originalId: @RawValue Any,
//    val preparationMinutes: Int,
//    val pricePerServing: Double,
//    @SerializedName("readyInMinutes")
//    val readyInMinutes: Int,
//    val servings: Int,
//    val sourceName: String,
//    val sourceUrl: String,
//    val spoonacularSourceUrl: String,
//    val summary: String,
//    val sustainable: Boolean,
//    val title: String,
//    val vegan: Boolean,
//    val vegetarian: Boolean,
//    val veryHealthy: Boolean,
//    val veryPopular: Boolean,
//    val weightWatcherSmartPoints: Int*/
