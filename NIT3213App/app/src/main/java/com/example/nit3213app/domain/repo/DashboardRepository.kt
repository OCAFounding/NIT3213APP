package com.example.nit3213app.domain.repo

import com.example.nit3213app.domain.model.DashboardData

/**
 * Repository interface for dashboard data operations
 * 
 * This interface defines the contract for dashboard data operations.
 * It abstracts the data source and provides a clean API for the domain layer.
 * 
 * Key Concepts:
 * - Repository pattern for data abstraction
 * - Domain layer interface
 * - Result wrapper for error handling
 * - Suspend functions for coroutine support
 * 
 * Assignment Requirements:
 * - Fetch dashboard data using keypass
 * - Returns Result<DashboardData> with entities and total count
 * - Used by ViewModels for dashboard data
 */
interface DashboardRepository {
    
    /**
     * Fetch dashboard data using authentication keypass
     * 
     * @param keypass Authentication key received from login
     * @return Result containing DashboardData on success, or failure with error
     */
    suspend fun fetchDashboard(keypass: String): Result<DashboardData>
}
