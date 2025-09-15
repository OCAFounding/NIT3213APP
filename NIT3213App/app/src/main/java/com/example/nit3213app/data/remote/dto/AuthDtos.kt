package com.example.nit3213app.data.remote.dto

import com.squareup.moshi.JsonClass

/**
 * Data Transfer Objects for Authentication API
 * 
 * These classes represent the JSON structure for API requests and responses.
 * They are separate from domain models to maintain clean architecture.
 * 
 * Key Concepts:
 * - DTOs represent network data structure
 * - Moshi annotations for JSON serialization
 * - Separate from domain models for flexibility
 * 
 * Assignment Requirements:
 * - Login request with username and password
 * - Login response with keypass
 * - Used by Retrofit for API communication
 */

/**
 * Request body for POST {campus}/auth endpoint
 * 
 * @param username Student's first name (e.g., "John")
 * @param password Student ID without 's' (e.g., "12345678")
 */
@JsonClass(generateAdapter = true)
data class AuthRequest(
    val username: String,
    val password: String
)

/**
 * Response from POST {campus}/auth endpoint
 * 
 * @param keypass Topic name returned on successful authentication
 *                Used to access dashboard endpoint
 */
@JsonClass(generateAdapter = true)
data class AuthResponse(
    val keypass: String
)
