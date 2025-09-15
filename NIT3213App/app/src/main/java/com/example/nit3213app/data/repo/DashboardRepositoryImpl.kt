package com.example.nit3213app.data.repo

import com.example.nit3213app.data.remote.ApiService
import com.example.nit3213app.data.remote.dto.toDomain
import com.example.nit3213app.domain.model.DashboardData
import com.example.nit3213app.domain.repo.DashboardRepository
import javax.inject.Inject

/**
 * Implementation of DashboardRepository interface
 * 
 * This class implements the dashboard repository using the ApiService.
 * It handles the network calls and converts DTOs to domain models.
 * 
 * Key Concepts:
 * - Repository pattern implementation
 * - Network error handling with Result wrapper
 * - DTO to domain model conversion
 * - Dependency injection with Hilt
 * 
 * Assignment Requirements:
 * - Implements DashboardRepository interface
 * - Uses ApiService for network calls
 * - Converts DTOs to domain models
 * - Returns Result<DashboardData> with entities and total count
 */
class DashboardRepositoryImpl @Inject constructor(
    private val api: ApiService
) : DashboardRepository {

    /**
     * Fetch dashboard data using authentication keypass
     * 
     * @param keypass Authentication key received from login
     * @return Result containing DashboardData on success, or failure with error
     */
    override suspend fun fetchDashboard(keypass: String): Result<DashboardData> = runCatching {
        // Make API call
        val response = api.getDashboard(keypass)
        
        // Convert DTOs to domain models
        val entities = response.entities.map { it.toDomain() }
        
        // Create domain model
        DashboardData(
            entities = entities,
            entityTotal = response.entityTotal
        )
    }
}
