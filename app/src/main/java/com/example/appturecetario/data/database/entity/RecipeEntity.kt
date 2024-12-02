package com.example.appturecetario.data.database.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.appturecetario.domain.model.Recipe

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val imageUrl: String,
    val summary: String,
    val ingredients: String,
    val instructions: String,
    val isFavorite: Boolean = false
) {
    fun toDomain() = Recipe(
        id = id,
        title = title,
        imageUrl = imageUrl,
        summary = summary,
        ingredients = if (ingredients.isBlank()) emptyList() else ingredients.split(","),
        instructions = instructions,
        isFavorite = isFavorite
    )
}