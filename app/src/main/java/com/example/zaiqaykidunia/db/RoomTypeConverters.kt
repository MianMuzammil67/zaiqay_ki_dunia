package com.example.zaiqaykidunia.db
import androidx.room.TypeConverter
import com.example.zaiqaykidunia.network.AnalyzedInstruction
import com.example.zaiqaykidunia.network.ExtendedIngredient
import com.example.zaiqaykidunia.network.Recipe
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomTypeConverters {
    @TypeConverter
    fun fromIngredient(ingredient: List<ExtendedIngredient>): String {
        return Gson().toJson(ingredient)
    }
    @TypeConverter
    fun toIngredient(string: String): List<ExtendedIngredient> {
        val type = object : TypeToken<List<ExtendedIngredient>>() {}.type
        return Gson().fromJson(string, type)
    }
    @TypeConverter
    fun fromInstruction(value: List<AnalyzedInstruction>): String {
        return Gson().toJson(value)
    }
    @TypeConverter
    fun toInstruction(value: String): List<AnalyzedInstruction> {
        val type = object :TypeToken<List<AnalyzedInstruction>>(){}.type
        return Gson().fromJson(value,type)
    }



    @TypeConverter
    fun fromRecipe(value: Recipe): String {
        return Gson().toJson(value)
    }
    @TypeConverter
    fun toRecipe(value: String): Recipe {
        val type = object :TypeToken<Recipe>(){}.type
        return Gson().fromJson(value,type)
    }
}