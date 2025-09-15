package com.example.nit3213app.domain.repo

/**
 * Repository interface for authentication operations
 * 
 * This interface defines the contract for authentication operations.
 * It follows the repository pattern to abstract data sources and
 * provides a clean API for the domain layer.
 * 
 * Key Concepts:
 * - Repository pattern for data abstraction
 * - Domain layer interface
 * - Result wrapper for error handling
 * - Suspend functions for coroutine support
 * 
 * Assignment Requirements:
 * - Login method with campus, username, and password
 * - Returns Result<String> for keypass
 * - Used by ViewModels for authentication
 */
interface AuthRepository {
    
    /**
     * Authenticate user with campus-specific endpoint
     * 
     * @param campus Campus location: "footscray", "sydney", or "br"
     * @param username Student's first name
     * @param password Student ID without 's' (e.g., "12345678")
     * @return Result containing keypass on success, or failure with error
     */
    suspend fun login(
        campus: String,
        username: String,
        password: String
    ): Result<String>
}
