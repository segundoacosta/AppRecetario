package com.example.appturecetario.ui.navigation


import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.graph.id) { inclusive = true }
}