package com.example.appturecetario.di

import android.content.Context
import com.example.appturecetario.data.api.SpoonacularApi
import com.example.appturecetario.data.database.dao.RecipeDao
import com.example.appturecetario.data.repository.RecipeRepositoryImpl
import com.example.appturecetario.domain.repository.RecipeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRecipeRepository(
        api: SpoonacularApi,
        dao: RecipeDao,
        @ApplicationContext context: Context
    ): RecipeRepository {
        return RecipeRepositoryImpl(api, dao, context)
    }
}