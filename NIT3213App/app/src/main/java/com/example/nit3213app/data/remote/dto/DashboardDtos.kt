package com.example.nit3213app.data.remote.dto

import com.squareup.moshi.JsonClass
import com.example.nit3213app.domain.model.EntityModel

/**
 * Data Transfer Objects for Dashboard API
 * 
 * These classes represent the JSON structure for dashboard API responses.
 * They include mapping functions to convert to domain models.
 * 
 * Key Concepts:
 * - DTOs represent network data structure
 * - Extension functions for mapping to domain models
 * - Moshi annotations for JSON serialization
 * 
 * Assignment Requirements:
 * - Dashboard response with entities and total count
 * - Entity DTO with all properties
 * - Mapping to domain models for clean architecture
 */

/**
 * Response from GET dashboard/{keypass} endpoint
 * 
 * @param entities List of entity objects from the API
 * @param entityTotal Total number of entities
 */
@JsonClass(generateAdapter = true)
data class DashboardResponse(
    val entities: List<EntityDto>,
    val entityTotal: Int
)

/**
 * Individual entity from dashboard API response
 * 
 * @param property1 First property of the entity
 * @param property2 Second property of the entity
 * @param description Detailed description of the entity
 */
@JsonClass(generateAdapter = true)
data class EntityDto(
    val property1: String,
    val property2: String,
    val description: String
)

/**
 * Extension function to convert EntityDto to EntityModel
 * 
 * This function maps the data layer DTO to the domain layer model,
 * maintaining separation of concerns in clean architecture.
 * 
 * @return EntityModel domain object
 */
fun EntityDto.toDomain(): EntityModel = EntityModel(
    property1 = property1,
    property2 = property2,
    description = description
)
