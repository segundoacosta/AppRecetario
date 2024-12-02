package com.example.appturecetario.ui.screens.recipes.detail


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appturecetario.domain.model.Recipe
import com.example.appturecetario.domain.model.UIState
import com.example.appturecetario.domain.usecase.GetRecipeDetailUseCase
import com.example.appturecetario.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    private val getRecipeDetailUseCase: GetRecipeDetailUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<Recipe>>(UIState.Loading)
    val uiState: StateFlow<UIState<Recipe>> = _uiState.asStateFlow()

    fun loadRecipe(recipeId: Int) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                val result = getRecipeDetailUseCase(recipeId)
                _uiState.value = when {
                    result.isSuccess -> UIState.Success(result.getOrNull()!!)
                    else -> UIState.Error(
                        result.exceptionOrNull()?.message ?: "Error al cargar la receta"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    fun toggleFavorite(recipeId: Int) {
        viewModelScope.launch {
            toggleFavoriteUseCase(recipeId).collect { result ->
                when (result) {
                    is ToggleFavoriteUseCase.Result.Success -> loadRecipe(recipeId)
                    is ToggleFavoriteUseCase.Result.Error -> {
                        _uiState.value = UIState.Error(result.message)
                    }
                    else -> Unit
                }
            }
        }
    }
}
