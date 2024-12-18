package com.example.appturecetario

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

import com.example.appturecetario.ui.navigation.NavigationApp
import com.example.appturecetario.ui.theme.AppTuRecetarioTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {
            AppTuRecetarioTheme {
                NavigationApp()
            }
        }
    }
}