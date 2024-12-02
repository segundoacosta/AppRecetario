package com.example.appturecetario.ui.screens.recipes


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appturecetario.domain.model.Recipe
import com.example.appturecetario.domain.model.UIState
import com.example.appturecetario.domain.usecase.GetFavoriteRecipesUseCase
import com.example.appturecetario.domain.usecase.GetRecipesUseCase
import com.example.appturecetario.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val getFavoriteRecipesUseCase: GetFavoriteRecipesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<Recipe>>>(UIState.Loading)
    val uiState: StateFlow<UIState<List<Recipe>>> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _isShowingFavorites = MutableStateFlow(false)
    val isShowingFavorites: StateFlow<Boolean> = _isShowingFavorites.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private var searchJob: Job? = null

    init {
        loadRecipes()
    }

    fun updateSearchQuery(query: String) {
        Log.d("RecipesViewModel", "Nueva búsqueda: $query")
        _searchQuery.value = query
        if (!_isShowingFavorites.value) {
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                delay(300) // Debounce de 300ms
                Log.d("RecipesViewModel", "Ejecutando búsqueda después del debounce: $query")
                loadRecipes(query)
            }
        }
    }

    fun toggleFavoriteFilter() {
        viewModelScope.launch {
            _isShowingFavorites.value = !_isShowingFavorites.value
            if (_isShowingFavorites.value) {
                loadFavoriteRecipes()
            } else {
                loadRecipes(_searchQuery.value)
            }
        }
    }

    fun toggleFavorite(recipeId: Int) {
        viewModelScope.launch {
            toggleFavoriteUseCase(recipeId).collect { result ->
                when (result) {
                    is ToggleFavoriteUseCase.Result.Success -> {
                        // Recargar la lista actual
                        if (_isShowingFavorites.value) {
                            loadFavoriteRecipes()
                        } else {
                            loadRecipes(_searchQuery.value)
                        }
                    }
                    is ToggleFavoriteUseCase.Result.Error -> {
                        _uiState.value = UIState.Error(result.message)
                    }
                    else -> Unit
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _isRefreshing.value = true
            try {
                if (_isShowingFavorites.value) {
                    loadFavoriteRecipes()
                } else {
                    loadRecipes(_searchQuery.value)
                }
            } finally {
                _isRefreshing.value = false
            }
        }
    }

    fun retryLastOperation() {
        if (_isShowingFavorites.value) {
            loadFavoriteRecipes()
        } else {
            loadRecipes(_searchQuery.value)
        }
    }


    private fun loadRecipes(query: String = "") {
        viewModelScope.launch {
            Log.d("RecipesViewModel", "LoadRecipes iniciado con query: $query")
            _uiState.value = UIState.Loading
            try {
                getRecipesUseCase(query).collect { result ->
                    Log.d("RecipesViewModel", "Resultado recibido del UseCase: $result")
                    _uiState.value = when {
                        result.isSuccess -> {
                            val recipes = result.getOrNull() ?: emptyList()
                            Log.d("RecipesViewModel", "Búsqueda exitosa, recetas encontradas: ${recipes.size}")
                            UIState.Success(recipes)
                        }
                        result.isFailure -> {
                            val error = result.exceptionOrNull()?.message ?: "Error desconocido"
                            Log.e("RecipesViewModel", "Error en búsqueda: $error")
                            UIState.Error(error)
                        }
                        else -> UIState.Loading
                    }
                }
            } catch (e: Exception) {
                Log.e("RecipesViewModel", "Excepción en loadRecipes: ${e.message}")
                _uiState.value = UIState.Error(e.message ?: "Error desconocido")
            }
        }
    }

    private fun loadFavoriteRecipes() {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                getFavoriteRecipesUseCase().collect { recipes ->
                    _uiState.value = UIState.Success(recipes)
                }
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "Error al cargar favoritos")
            }
        }
    }
}