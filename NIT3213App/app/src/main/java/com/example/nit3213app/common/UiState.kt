package com.example.nit3213app.common

/**
 * Sealed interface for UI state management
 * 
 * This sealed interface provides a type-safe way to represent
 * different states of UI operations (loading, success, error).
 * It's used throughout the app for consistent state management.
 * 
 * Key Concepts:
 * - Sealed interface for type safety
 * - Generic type parameter for data
 * - Immutable state objects
 * - Pattern matching with when expressions
 * 
 * Assignment Requirements:
 * - Used by ViewModels for state management
 * - Provides consistent UI state handling
 * - Enables reactive UI updates
 * - Supports error handling
 */
sealed interface UiState<out T> {
    
    /**
     * Initial state when no operation has been performed
     */
    object Idle : UiState<Nothing>
    
    /**
     * Loading state when an operation is in progress
     */
    object Loading : UiState<Nothing>
    
    /**
     * Success state with data
     * 
     * @param data The successful result data
     */
    data class Success<T>(val data: T) : UiState<T>
    
    /**
     * Error state with error message
     * 
     * @param message Error message to display to user
     */
    data class Error(val message: String) : UiState<Nothing>
}
