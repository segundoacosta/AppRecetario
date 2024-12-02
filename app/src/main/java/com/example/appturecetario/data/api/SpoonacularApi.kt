package com.example.appturecetario.data.api

import com.example.appturecetario.data.api.model.RecipeDetailResponse
import com.example.appturecetario.data.api.model.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface SpoonacularApi {
    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("apiKey") apiKey: String,
        @Query("query") query: String = "",
        @Query("number") number: Int = 20
    ): RecipeResponse

    @GET("recipes/{id}/information")
    suspend fun getRecipeDetail(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String
    ): RecipeDetailResponse
}