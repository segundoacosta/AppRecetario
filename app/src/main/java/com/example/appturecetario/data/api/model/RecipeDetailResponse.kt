package com.example.appturecetario.data.api.model


import com.google.gson.annotations.SerializedName

data class RecipeDetailResponse(
    val id: Int,
    val title: String,
    @SerializedName("image")
    val imageUrl: String,
    val summary: String,
    val instructions: String?,
    val extendedIngredients: List<IngredientDto>
)

data class IngredientDto(
    val id: Int,
    val name: String,
    val amount: Double,
    val unit: String
)
