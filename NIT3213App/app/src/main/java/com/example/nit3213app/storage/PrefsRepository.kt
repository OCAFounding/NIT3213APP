package com.example.nit3213app.storage

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository for managing app preferences using DataStore
 * 
 * This class provides a clean interface for storing and retrieving
 * app preferences like campus selection and authentication keypass.
 * 
 * Key Concepts:
 * - DataStore for modern preference storage
 * - Flow-based reactive data access
 * - Hilt dependency injection
 * - Type-safe preference keys
 * 
 * Assignment Requirements:
 * - Stores campus selection (footscray, sydney, br)
 * - Stores authentication keypass
 * - Provides reactive data access with Flow
 * - Used by ViewModels for persistence
 */
@Singleton
class PrefsRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    
    /**
     * Preference keys for DataStore
     * 
     * These keys define the structure of stored preferences
     */
    private object Keys {
        val CAMPUS = stringPreferencesKey("campus")
        val KEYPASS = stringPreferencesKey("keypass")
    }

    /**
     * Flow of campus selection
     * 
     * @return Flow<String?> that emits the current campus selection
     */
    val campusFlow: Flow<String?> =
        context.dataStore.data.map { preferences ->
            preferences[Keys.CAMPUS]
        }

    /**
     * Flow of authentication keypass
     * 
     * @return Flow<String?> that emits the current keypass
     */
    val keypassFlow: Flow<String?> =
        context.dataStore.data.map { preferences ->
            preferences[Keys.KEYPASS]
        }

    /**
     * Save campus selection to preferences
     * 
     * @param campus Campus location: "footscray", "sydney", or "br"
     */
    suspend fun saveCampus(campus: String) {
        context.dataStore.edit { preferences ->
            preferences[Keys.CAMPUS] = campus
        }
    }

    /**
     * Save authentication keypass to preferences
     * 
     * @param keypass Authentication key received from login
     */
    suspend fun saveKeypass(keypass: String) {
        context.dataStore.data.edit { preferences ->
            preferences[Keys.KEYPASS] = keypass
        }
    }
}
