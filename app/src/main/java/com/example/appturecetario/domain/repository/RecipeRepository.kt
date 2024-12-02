package com.example.appturecetario.domain.repository


import com.example.appturecetario.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun searchRecipes(query: String): Flow<Result<List<Recipe>>>
    suspend fun getRecipeDetail(id: Int): Result<Recipe>
    suspend fun toggleFavorite(id: Int)
    fun getFavoriteRecipes(): Flow<List<Recipe>>
}