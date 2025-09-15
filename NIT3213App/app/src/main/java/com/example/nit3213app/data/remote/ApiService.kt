package com.example.nit3213app.data.remote

import com.example.nit3213app.data.remote.dto.AuthRequest
import com.example.nit3213app.data.remote.dto.AuthResponse
import com.example.nit3213app.data.remote.dto.DashboardResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Retrofit API service for NIT3213 endpoints
 * 
 * This interface defines the API endpoints used by the application.
 * Retrofit automatically generates the implementation based on these annotations.
 * 
 * Key Concepts:
 * - Retrofit interface for HTTP API calls
 * - Suspend functions for coroutine support
 * - Path parameters for dynamic URLs
 * - Body annotations for request data
 * 
 * Assignment Requirements:
 * - Login endpoint with campus-specific URLs
 * - Dashboard endpoint with keypass parameter
 * - Proper error handling through Result wrapper
 * 
 * API Base URL: https://nit3213api.onrender.com/
 */
interface ApiService {

    /**
     * Authenticate user with campus-specific endpoint
     * 
     * @param campus Campus location: "footscray", "sydney", or "br"
     * @param body Authentication request with username and password
     * @return AuthResponse containing keypass on success
     * 
     * Endpoint: POST /{campus}/auth
     * Example: POST /footscray/auth
     */
    @POST("{campus}/auth")
    suspend fun login(
        @Path("campus") campus: String,
        @Body body: AuthRequest
    ): AuthResponse

    /**
     * Retrieve dashboard data using keypass
     * 
     * @param keypass Authentication key received from login
     * @return DashboardResponse containing entities and total count
     * 
     * Endpoint: GET /dashboard/{keypass}
     * Example: GET /dashboard/topicName
     */
    @GET("dashboard/{keypass}")
    suspend fun getDashboard(
        @Path("keypass") keypass: String
    ): DashboardResponse
}
