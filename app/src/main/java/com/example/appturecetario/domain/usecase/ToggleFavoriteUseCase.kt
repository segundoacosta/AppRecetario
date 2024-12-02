package com.example.appturecetario.domain.usecase

import com.example.appturecetario.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    /**
     * Alterna el estado de favorito de una receta
     *
     * @param recipeId ID de la receta a modificar
     * @return Flow que emite el éxito o error de la operación
     */
    suspend operator fun invoke(recipeId: Int): Flow<Result<Unit>> = flow {
        try {
            emit(Result.Loading)
            repository.toggleFavorite(recipeId)
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Error al modificar favorito"))
        }
    }.catch { e ->
        emit(Result.Error(e.message ?: "Error inesperado"))
    }

    sealed class Result<out T> {
        object Loading : Result<Nothing>()
        data class Success<T>(val data: T) : Result<T>()
        data class Error(val message: String) : Result<Nothing>()
    }
}