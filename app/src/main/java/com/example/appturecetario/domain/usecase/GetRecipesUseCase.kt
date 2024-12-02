package com.example.appturecetario.domain.usecase


import com.example.appturecetario.domain.model.Recipe
import com.example.appturecetario.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(query: String = ""): Flow<Result<List<Recipe>>> {
        return repository.searchRecipes(query)
    }
}