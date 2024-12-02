package com.example.appturecetario.data.repository


import android.content.Context
import android.util.Log
import com.example.appturecetario.BuildConfig
import com.example.appturecetario.data.api.SpoonacularApi
import com.example.appturecetario.data.api.model.RecipeDetailResponse
import com.example.appturecetario.data.api.model.RecipeDto
import com.example.appturecetario.data.database.dao.RecipeDao
import com.example.appturecetario.data.database.entity.RecipeEntity
import com.example.appturecetario.domain.model.Recipe
import com.example.appturecetario.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepositoryImpl @Inject constructor(
    private val api: SpoonacularApi,
    private val dao: RecipeDao,
    private val context: Context
) : RecipeRepository {

    override suspend fun searchRecipes(query: String): Flow<Result<List<Recipe>>> = flow {
        Log.d("RecipeRepository", "Iniciando búsqueda en API con query: $query")
        try {
            val response = api.searchRecipes(
                apiKey = BuildConfig.API_KEY,
                query = query
            )
            Log.d(
                "RecipeRepository",
                "Respuesta recibida de API, resultados: ${response.results.size}"
            )

            val recipes = response.results.map { dto ->
                Recipe(
                    id = dto.id,
                    title = dto.title,
                    imageUrl = dto.imageUrl,
                    summary = dto.summary ?: "", // Manejamos el caso null
                    ingredients = emptyList(),
                    instructions = "",
                    isFavorite = false
                )
            }

            Log.d("RecipeRepository", "Mapeo de recetas completado")
            emit(Result.success(recipes))
        } catch (e: Exception) {
            Log.e("RecipeRepository", "Error en búsqueda: ${e.message}", e)
            emit(Result.failure(e))
        }
    }

    override suspend fun getRecipeDetail(id: Int): Result<Recipe> {
        return try {
            // Primero intentamos obtener de la base de datos local
            val localRecipe = dao.getRecipeById(id)
            if (localRecipe != null) {
                Result.success(localRecipe.toDomain())
            } else {
                // Si no está en local, lo buscamos en la API
                val response = api.getRecipeDetail(
                    id = id,
                    apiKey = BuildConfig.API_KEY
                )
                val recipe = response.toDomain()
                // Guardamos en la base de datos local
                dao.insertRecipe(recipe.toRecipeEntity())
                Result.success(recipe)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun toggleFavorite(id: Int) {
        val recipe = dao.getRecipeById(id)
        recipe?.let {
            dao.updateRecipe(it.copy(isFavorite = !it.isFavorite))
        }
    }

    override fun getFavoriteRecipes(): Flow<List<Recipe>> {
        return dao.getFavoriteRecipes().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    // Extension functions para mapear entre modelos
    private fun RecipeDto.toEntity() = RecipeEntity(
        id = id,
        title = title,
        imageUrl = imageUrl,
        summary = summary ?: "",
        ingredients = extendedIngredients?.joinToString(",") { "${it.amount} ${it.unit} ${it.name}" }
            ?: "",
        instructions = instructions ?: "",
        isFavorite = false
    )

    private fun RecipeEntity.toDomain() = Recipe(
        id = id,
        title = title,
        imageUrl = imageUrl,
        summary = summary,
        ingredients = if (ingredients.isBlank()) emptyList() else ingredients.split(","),
        instructions = instructions,
        isFavorite = isFavorite
    )


    private fun RecipeDetailResponse.toDomain() = Recipe(
        id = id,
        title = title,
        imageUrl = imageUrl,
        summary = summary,
        ingredients = extendedIngredients.map { "${it.amount} ${it.unit} ${it.name}" },
        instructions = instructions ?: ""
    )

    private fun Recipe.toRecipeEntity() = RecipeEntity(
        id = id,
        title = title,
        imageUrl = imageUrl,
        summary = summary,
        ingredients = ingredients.joinToString(","),
        instructions = instructions,
        isFavorite = isFavorite
    )
}