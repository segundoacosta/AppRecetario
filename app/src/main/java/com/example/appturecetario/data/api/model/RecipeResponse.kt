package com.example.appturecetario.data.api.model


import com.google.gson.annotations.SerializedName

data class RecipeResponse(
    val results: List<RecipeDto>,
    val offset: Int,
    val number: Int,
    val totalResults: Int
)

data class RecipeDto(
    val id: Int,
    val title: String,
    @SerializedName("image")
    val imageUrl: String,
    val summary: String? = null,
    val extendedIngredients: List<IngredientDto>? = null,
    val instructions: String? = null
)
