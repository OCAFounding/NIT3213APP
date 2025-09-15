package com.example.nit3213app.di

import com.example.nit3213app.data.repo.AuthRepositoryImpl
import com.example.nit3213app.data.repo.DashboardRepositoryImpl
import com.example.nit3213app.domain.repo.AuthRepository
import com.example.nit3213app.domain.repo.DashboardRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for repository dependencies
 * 
 * This module binds repository interfaces to their implementations.
 * It follows the dependency inversion principle by depending on abstractions
 * rather than concrete implementations.
 * 
 * Key Concepts:
 * - Interface binding for dependency inversion
 * - Singleton scope for repository instances
 * - Clean architecture separation
 * 
 * Assignment Requirements:
 * - Binds AuthRepository interface to implementation
 * - Binds DashboardRepository interface to implementation
 * - Uses Hilt for dependency injection
 * - Follows clean architecture principles
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    /**
     * Binds AuthRepository interface to AuthRepositoryImpl
     * 
     * @param impl Concrete implementation of AuthRepository
     * @return AuthRepository interface
     */
    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    /**
     * Binds DashboardRepository interface to DashboardRepositoryImpl
     * 
     * @param impl Concrete implementation of DashboardRepository
     * @return DashboardRepository interface
     */
    @Binds
    @Singleton
    abstract fun bindDashboardRepository(impl: DashboardRepositoryImpl): DashboardRepository
}
