package com.example.appturecetario.domain.model

data class Recipe(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val summary: String,
    val ingredients: List<String> = emptyList(),
    val instructions: String = "",
    val isFavorite: Boolean = false
)