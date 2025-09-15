package com.example.nit3213app.storage

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

/**
 * DataStore extension for NIT3213App preferences
 * 
 * This file provides a DataStore instance for storing app preferences.
 * DataStore is the modern replacement for SharedPreferences, providing
 * type-safe, asynchronous data storage.
 * 
 * Key Concepts:
 * - DataStore for modern preference storage
 * - Type-safe data access
 * - Asynchronous operations
 * - Context extension property
 * 
 * Assignment Requirements:
 * - Stores campus selection and keypass
 * - Used for persistence across app sessions
 * - Modern replacement for SharedPreferences
 */

/**
 * DataStore delegate bound to the app Context
 * 
 * This creates a DataStore instance with the name "nit3213_prefs"
 * that will be stored in the app's private data directory.
 */
val Context.dataStore by preferencesDataStore("nit3213_prefs")
