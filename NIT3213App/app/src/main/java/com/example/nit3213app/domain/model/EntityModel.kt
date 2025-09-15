package com.example.nit3213app.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Domain model representing an entity from the NIT3213 API
 * 
 * This is the domain layer model that represents the business logic
 * and is used throughout the application. It's separate from the
 * data layer DTOs to maintain clean architecture principles.
 * 
 * Key Concepts:
 * - Domain models represent business entities
 * - Parcelable allows passing between activities/fragments
 * - Immutable data class for thread safety
 * 
 * Assignment Requirements:
 * - Represents entities from dashboard API response
 * - Used in RecyclerView and Details screen
 * - Passed between screens via Navigation Safe Args
 */
@Parcelize
data class EntityModel(
    val property1: String,
    val property2: String,
    val description: String
) : Parcelable

/**
 * Domain model representing the complete dashboard data
 * 
 * Contains the list of entities and the total count returned
 * from the dashboard API endpoint.
 */
data class DashboardData(
    val entities: List<EntityModel>,
    val entityTotal: Int
)
