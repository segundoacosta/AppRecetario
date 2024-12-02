package com.example.appturecetario.ui.screens.onboarding


import androidx.lifecycle.ViewModel
import com.example.appturecetario.R
import com.example.appturecetario.domain.model.OnboardingPage
import com.example.appturecetario.domain.usecase.SetOnboardingStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val setOnboardingStatusUseCase: SetOnboardingStatusUseCase
) : ViewModel() {

    fun getOnboardingPages(): List<OnboardingPage> = listOf(
        OnboardingPage(
            title = "Descubre Recetas",
            description = "Explora miles de recetas de todo el mundo",
            imageRes = R.drawable.ic_launcher_background
        ),
        OnboardingPage(
            title = "Guarda tus Favoritos",
            description = "Marca las recetas que más te gusten",
            imageRes = R.drawable.ic_launcher_background
        ),
        OnboardingPage(
            title = "Cocina con Facilidad",
            description = "Sigue paso a paso las instrucciones",
            imageRes = R.drawable.ic_launcher_background
        )
    )

    fun completeOnboarding() {
        setOnboardingStatusUseCase(true)
    }
}
