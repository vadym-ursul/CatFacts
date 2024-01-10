package com.sampleapps.catfacts.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Storage @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val IS_ONBOARDING_PASSED = booleanPreferencesKey("is_onboarding_passed")
        const val TAG = "Storage"
    }

    suspend fun saveOnboardingPassed(isOnboardingPassed: Boolean) {
        dataStore.edit { prefs ->
            prefs[IS_ONBOARDING_PASSED] = isOnboardingPassed
        }
    }

    val isOnBoardPassed: Flow<Boolean> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[IS_ONBOARDING_PASSED] ?: false
        }
}