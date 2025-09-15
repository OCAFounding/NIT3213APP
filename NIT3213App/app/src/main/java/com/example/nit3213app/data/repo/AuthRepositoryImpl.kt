package com.example.nit3213app.data.repo

import com.example.nit3213app.data.remote.ApiService
import com.example.nit3213app.data.remote.dto.AuthRequest
import com.example.nit3213app.domain.repo.AuthRepository
import javax.inject.Inject

/**
 * Implementation of AuthRepository interface
 * 
 * This class implements the authentication repository using the ApiService.
 * It handles the network calls and error handling for authentication operations.
 * 
 * Key Concepts:
 * - Repository pattern implementation
 * - Network error handling with Result wrapper
 * - DTO to domain model conversion
 * - Dependency injection with Hilt
 * 
 * Assignment Requirements:
 * - Implements AuthRepository interface
 * - Uses ApiService for network calls
 * - Handles authentication errors
 * - Returns Result<String> for keypass
 */
class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService
) : AuthRepository {

    /**
     * Authenticate user with campus-specific endpoint
     * 
     * @param campus Campus location: "footscray", "sydney", or "br"
     * @param username Student's first name
     * @param password Student ID without 's' (e.g., "12345678")
     * @return Result containing keypass on success, or failure with error
     */
    override suspend fun login(
        campus: String,
        username: String,
        password: String
    ): Result<String> = runCatching {
        // Create request DTO
        val request = AuthRequest(username = username, password = password)
        
        // Make API call
        val response = api.login(campus, request)
        
        // Return keypass from response
        response.keypass
    }
}
