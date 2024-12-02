package com.example.appturecetario.ui.screens.splash

import androidx.lifecycle.ViewModel
import com.example.appturecetario.domain.usecase.GetOnboardingStatusUseCase

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getOnboardingStatusUseCase: GetOnboardingStatusUseCase
) : ViewModel() {
    fun shouldShowOnboarding(): Boolean = !getOnboardingStatusUseCase()
}