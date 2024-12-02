package com.example.appturecetario.domain.usecase


import com.example.appturecetario.domain.model.Recipe
import com.example.appturecetario.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    operator fun invoke(): Flow<List<Recipe>> = repository.getFavoriteRecipes()
}