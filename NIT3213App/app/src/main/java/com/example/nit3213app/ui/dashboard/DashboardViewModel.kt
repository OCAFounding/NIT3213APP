package com.example.nit3213app.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nit3213app.common.UiState
import com.example.nit3213app.domain.model.DashboardData
import com.example.nit3213app.domain.repo.DashboardRepository
import com.example.nit3213app.storage.PrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

/**
 * ViewModel for Dashboard screen
 * 
 * This ViewModel handles the dashboard data loading and state management.
 * It communicates with the DashboardRepository to fetch entities data
 * and manages the UI state for the dashboard screen.
 * 
 * Key Concepts:
 * - ViewModel for UI state management
 * - StateFlow for reactive state updates
 * - Coroutines for asynchronous operations
 * - Hilt dependency injection
 * - Repository pattern for data access
 * 
 * Assignment Requirements:
 * - Loads dashboard data using saved keypass
 * - Manages loading, success, and error states
 * - Provides data for RecyclerView display
 * - Handles authentication state validation
 */
@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val dashboardRepo: DashboardRepository,
    private val prefs: PrefsRepository
) : ViewModel() {

    // =============================================================================
    // STATE MANAGEMENT
    // =============================================================================
    
    /**
     * Private mutable state flow for dashboard state
     * Only the ViewModel can modify this state
     */
    private val _state = MutableStateFlow<UiState<DashboardData>>(UiState.Loading)
    
    /**
     * Public read-only state flow for UI observation
     * Fragments can observe this to update the UI
     */
    val state: StateFlow<UiState<DashboardData>> = _state

    // =============================================================================
    // INITIALIZATION
    // =============================================================================
    
    /**
     * Initialize ViewModel and load dashboard data
     * 
     * This method is called when the ViewModel is created and
     * automatically loads the dashboard data using the saved keypass.
     */
    init {
        loadDashboardData()
    }

    // =============================================================================
    // DATA LOADING
    // =============================================================================
    
    /**
     * Load dashboard data from the API
     * 
     * This method retrieves the saved keypass from preferences
     * and uses it to fetch dashboard data from the API.
     */
    private fun loadDashboardData() {
        viewModelScope.launch {
            // Get saved keypass from preferences
            val keypass = prefs.keypassFlow.firstOrNull()
            
            if (keypass.isNullOrBlank()) {
                _state.value = UiState.Error("No authentication found. Please login again.")
                return@launch
            }
            
            // Fetch dashboard data from repository
            dashboardRepo.fetchDashboard(keypass)
                .onSuccess { dashboardData ->
                    _state.value = UiState.Success(dashboardData)
                }
                .onFailure { exception ->
                    _state.value = UiState.Error(
                        exception.message ?: "Failed to load dashboard data"
                    )
                }
        }
    }
    
    /**
     * Retry loading dashboard data
     * 
     * This method can be called to retry loading data
     * in case of network errors or other failures.
     */
    fun retry() {
        _state.value = UiState.Loading
        loadDashboardData()
    }
}
