package com.example.appturecetario.domain.repository


interface PreferencesRepository {
    fun getOnboardingCompleted(): Boolean
    fun setOnboardingCompleted(completed: Boolean)
}