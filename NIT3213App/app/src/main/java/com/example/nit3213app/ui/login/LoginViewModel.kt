package com.example.nit3213app.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nit3213app.common.UiState
import com.example.nit3213app.domain.repo.AuthRepository
import com.example.nit3213app.storage.PrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for Login screen
 * 
 * This ViewModel handles the login logic and state management.
 * It communicates with the AuthRepository to authenticate users
 * and manages the UI state for the login screen.
 * 
 * Key Concepts:
 * - ViewModel for UI state management
 * - StateFlow for reactive state updates
 * - Coroutines for asynchronous operations
 * - Hilt dependency injection
 * - Repository pattern for data access
 * 
 * Assignment Requirements:
 * - Handles login with campus, username, and password
 * - Validates input before making API calls
 * - Manages loading, success, and error states
 * - Persists campus and keypass for later use
 */
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepo: AuthRepository,
    private val prefs: PrefsRepository
) : ViewModel() {

    // =============================================================================
    // STATE MANAGEMENT
    // =============================================================================
    
    /**
     * Private mutable state flow for login state
     * Only the ViewModel can modify this state
     */
    private val _state = MutableStateFlow<UiState<Unit>>(UiState.Idle)
    
    /**
     * Public read-only state flow for UI observation
     * Fragments can observe this to update the UI
     */
    val state: StateFlow<UiState<Unit>> = _state

    // =============================================================================
    // LOGIN LOGIC
    // =============================================================================
    
    /**
     * Attempt to login with provided credentials
     * 
     * @param campus Campus location: "footscray", "sydney", or "br"
     * @param username Student's first name
     * @param password Student ID without 's' (e.g., "12345678")
     */
    fun login(campus: String, username: String, password: String) {
        // Validate input before making network call
        if (username.isBlank() || password.isBlank()) {
            _state.value = UiState.Error("Username and password are required.")
            return
        }
        
        // Validate password format (should be numeric Student ID)
        if (!password.matches(Regex("^\\d{6,10}$"))) {
            _state.value = UiState.Error("Password must be your numeric Student ID (no 's').")
            return
        }
        
        // Validate campus selection
        if (campus !in listOf("footscray", "sydney", "br")) {
            _state.value = UiState.Error("Please select a valid campus.")
            return
        }

        // Start login process
        viewModelScope.launch {
            _state.value = UiState.Loading
            
            // Call repository to authenticate
            authRepo.login(campus, username, password)
                .onSuccess { keypass ->
                    // Save campus and keypass for later use
                    prefs.saveCampus(campus)
                    prefs.saveKeypass(keypass)
                    
                    // Update state to success
                    _state.value = UiState.Success(Unit)
                }
                .onFailure { exception ->
                    // Update state with error message
                    _state.value = UiState.Error(
                        exception.message ?: "Login failed. Please check your credentials."
                    )
                }
        }
    }
}
