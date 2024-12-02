package com.example.appturecetario.domain.usecase


import com.example.appturecetario.domain.model.Recipe
import com.example.appturecetario.domain.repository.RecipeRepository
import javax.inject.Inject

class GetRecipeDetailUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    /**
     * Obtiene los detalles de una receta específica por su ID
     *
     * @param recipeId ID de la receta a buscar
     * @return Result que encapsula la receta o el error
     */
    suspend operator fun invoke(recipeId: Int): Result<Recipe> {
        return try {
            repository.getRecipeDetail(recipeId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}