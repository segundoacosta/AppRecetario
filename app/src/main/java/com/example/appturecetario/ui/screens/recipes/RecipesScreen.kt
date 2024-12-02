package com.example.appturecetario.ui.screens.recipes


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appturecetario.domain.model.Recipe
import com.example.appturecetario.domain.model.UIState
import com.example.appturecetario.ui.components.RecipeItem
import com.example.appturecetario.ui.components.SearchBar
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState



@Composable
fun RecipesScreen(
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RecipesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val isShowingFavorites by viewModel.isShowingFavorites.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    Scaffold(
        topBar = {
            RecipesTopBar(
                searchQuery = searchQuery,
                onQueryChange = viewModel::updateSearchQuery,
                isShowingFavorites = isShowingFavorites,
                onFavoriteClick = viewModel::toggleFavoriteFilter
            )
        },
        modifier = modifier
    ) { paddingValues ->
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = viewModel::refresh,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                when (val state = uiState) {
                    is UIState.Loading -> {
                        LoadingState(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    is UIState.Success -> {
                        if (state.data.isEmpty()) {
                            EmptyState(
                                isShowingFavorites = isShowingFavorites,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        } else {
                            RecipesList(
                                recipes = state.data,
                                onRecipeClick = navigateToDetail,
                                onFavoriteClick = viewModel::toggleFavorite
                            )
                        }
                    }
                    is UIState.Error -> {
                        ErrorState(
                            message = state.message,
                            onRetry = viewModel::retryLastOperation,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecipesTopBar(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    isShowingFavorites: Boolean,
    onFavoriteClick: () -> Unit
) {
    Column {
        TopAppBar(
            title = {
                Text(text = if (isShowingFavorites) "Favoritos" else "Recetas")
            },
            actions = {
                IconToggleButton(
                    checked = isShowingFavorites,
                    onCheckedChange = { onFavoriteClick() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = if (isShowingFavorites) {
                            "Mostrar todas las recetas"
                        } else {
                            "Mostrar favoritos"
                        },
                        tint = if (isShowingFavorites) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        }
                    )
                }
            }
        )
        SearchBar(
            query = searchQuery,
            onQueryChange = onQueryChange,
            modifier = Modifier.fillMaxWidth()
        )
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RecipesList(
    recipes: List<Recipe>,
    onRecipeClick: (Int) -> Unit,
    onFavoriteClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(
            items = recipes,
            key = { it.id }
        ) { recipe ->
            RecipeItem(
                recipe = recipe,
                onClick = { onRecipeClick(recipe.id) },
                onFavoriteClick = { onFavoriteClick(recipe.id) },
                modifier = Modifier.animateItemPlacement()
            )
        }
    }
}

@Composable
private fun LoadingState(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier
    )
}

@Composable
private fun EmptyState(
    isShowingFavorites: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (isShowingFavorites) {
                "No tienes recetas favoritas"
            } else {
                "No se encontraron recetas"
            },
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (isShowingFavorites) {
                "Marca algunas recetas como favoritas para verlas aquí"
            } else {
                "Intenta con una búsqueda diferente"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
    }
}

@Composable
private fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Reintentar")
        }
    }
}