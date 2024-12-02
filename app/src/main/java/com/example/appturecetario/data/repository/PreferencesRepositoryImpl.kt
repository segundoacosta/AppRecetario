package com.example.appturecetario.data.repository


import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.appturecetario.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(name = "preferences")

class PreferencesRepositoryImpl @Inject constructor(
    private val context: Context
) : PreferencesRepository {

    companion object {
        private val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
    }

    override fun getOnboardingCompleted(): Boolean = runBlocking {
        context.dataStore.data.first()[ONBOARDING_COMPLETED] ?: false
    }

    override fun setOnboardingCompleted(completed: Boolean) {
        runBlocking {
            context.dataStore.edit { preferences ->
                preferences[ONBOARDING_COMPLETED] = completed
            }
        }
    }
}