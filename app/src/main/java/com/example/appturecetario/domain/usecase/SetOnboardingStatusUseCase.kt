package com.example.appturecetario.domain.usecase


import com.example.appturecetario.domain.repository.PreferencesRepository
import javax.inject.Inject

class SetOnboardingStatusUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    operator fun invoke(completed: Boolean) {
        preferencesRepository.setOnboardingCompleted(completed)
    }
}