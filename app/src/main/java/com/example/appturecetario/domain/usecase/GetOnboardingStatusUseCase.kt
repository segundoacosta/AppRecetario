package com.example.appturecetario.domain.usecase


import com.example.appturecetario.domain.repository.PreferencesRepository
import javax.inject.Inject

class GetOnboardingStatusUseCase @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) {
    operator fun invoke(): Boolean = preferencesRepository.getOnboardingCompleted()
}