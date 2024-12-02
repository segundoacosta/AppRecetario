package com.example.appturecetario.di

import com.example.appturecetario.domain.repository.RecipeRepository
import com.example.appturecetario.domain.usecase.GetRecipeDetailUseCase
import com.example.appturecetario.domain.usecase.GetRecipesUseCase
import com.example.appturecetario.domain.usecase.ToggleFavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    fun provideGetRecipesUseCase(
        repository: RecipeRepository
    ): GetRecipesUseCase {
        return GetRecipesUseCase(repository)
    }

    @Provides
    fun provideGetRecipeDetailUseCase(
        repository: RecipeRepository
    ): GetRecipeDetailUseCase {
        return GetRecipeDetailUseCase(repository)
    }

    @Provides
    fun provideToggleFavoriteUseCase(
        repository: RecipeRepository
    ): ToggleFavoriteUseCase {
        return ToggleFavoriteUseCase(repository)
    }
}