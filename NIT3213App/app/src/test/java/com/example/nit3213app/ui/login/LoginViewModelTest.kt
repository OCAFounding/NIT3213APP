package com.example.nit3213app.ui.login

import com.example.nit3213app.common.UiState
import com.example.nit3213app.domain.repo.AuthRepository
import com.example.nit3213app.storage.PrefsRepository
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for LoginViewModel
 * 
 * These tests verify the login functionality and state management
 * of the LoginViewModel. They use mock objects to isolate the
 * ViewModel from its dependencies.
 * 
 * Key Concepts:
 * - Unit testing with JUnit
 * - Mocking dependencies with MockK
 * - Testing coroutines with TestDispatcher
 * - State verification
 * 
 * Assignment Requirements:
 * - Unit tests for critical components
 * - Tests for ViewModel state management
 * - Tests for success and failure scenarios
 * - Proper test isolation and mocking
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    // =============================================================================
    // TEST SETUP
    // =============================================================================
    
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var authRepository: AuthRepository
    private lateinit var prefsRepository: PrefsRepository
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        // Set main dispatcher for testing
        Dispatchers.setMain(testDispatcher)
        
        // Create mock dependencies
        authRepository = mockk(relaxed = true)
        prefsRepository = mockk(relaxed = true)
        
        // Create ViewModel with mocked dependencies
        viewModel = LoginViewModel(authRepository, prefsRepository)
    }

    @After
    fun tearDown() {
        // Reset main dispatcher
        Dispatchers.resetMain()
    }

    // =============================================================================
    // SUCCESS SCENARIO TESTS
    // =============================================================================
    
    @Test
    fun `login with valid credentials updates state to Success`() = runTest {
        // Given
        val campus = "footscray"
        val username = "John"
        val password = "12345678"
        val keypass = "topicName"
        
        // Mock successful authentication
        coEvery { authRepository.login(campus, username, password) } returns Result.success(keypass)
        
        // When
        viewModel.login(campus, username, password)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then
        assertTrue(viewModel.state.value is UiState.Success)
        coVerify { prefsRepository.saveCampus(campus) }
        coVerify { prefsRepository.saveKeypass(keypass) }
    }

    // =============================================================================
    // VALIDATION TESTS
    // =============================================================================
    
    @Test
    fun `login with blank username shows error`() = runTest {
        // Given
        val campus = "footscray"
        val username = ""
        val password = "12345678"
        
        // When
        viewModel.login(campus, username, password)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then
        assertTrue(viewModel.state.value is UiState.Error)
        assertEquals("Username and password are required.", (viewModel.state.value as UiState.Error).message)
    }
    
    @Test
    fun `login with blank password shows error`() = runTest {
        // Given
        val campus = "footscray"
        val username = "John"
        val password = ""
        
        // When
        viewModel.login(campus, username, password)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then
        assertTrue(viewModel.state.value is UiState.Error)
        assertEquals("Username and password are required.", (viewModel.state.value as UiState.Error).message)
    }
    
    @Test
    fun `login with invalid password format shows error`() = runTest {
        // Given
        val campus = "footscray"
        val username = "John"
        val password = "invalid"
        
        // When
        viewModel.login(campus, username, password)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then
        assertTrue(viewModel.state.value is UiState.Error)
        assertEquals("Password must be your numeric Student ID (no 's').", (viewModel.state.value as UiState.Error).message)
    }
    
    @Test
    fun `login with invalid campus shows error`() = runTest {
        // Given
        val campus = "invalid"
        val username = "John"
        val password = "12345678"
        
        // When
        viewModel.login(campus, username, password)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then
        assertTrue(viewModel.state.value is UiState.Error)
        assertEquals("Please select a valid campus.", (viewModel.state.value as UiState.Error).message)
    }

    // =============================================================================
    // FAILURE SCENARIO TESTS
    // =============================================================================
    
    @Test
    fun `login with invalid credentials shows error`() = runTest {
        // Given
        val campus = "footscray"
        val username = "John"
        val password = "12345678"
        val errorMessage = "Invalid credentials"
        
        // Mock failed authentication
        coEvery { authRepository.login(campus, username, password) } returns Result.failure(Exception(errorMessage))
        
        // When
        viewModel.login(campus, username, password)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then
        assertTrue(viewModel.state.value is UiState.Error)
        assertEquals(errorMessage, (viewModel.state.value as UiState.Error).message)
    }
    
    @Test
    fun `login with network error shows generic error message`() = runTest {
        // Given
        val campus = "footscray"
        val username = "John"
        val password = "12345678"
        
        // Mock network error
        coEvery { authRepository.login(campus, username, password) } returns Result.failure(Exception())
        
        // When
        viewModel.login(campus, username, password)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Then
        assertTrue(viewModel.state.value is UiState.Error)
        assertEquals("Login failed. Please check your credentials.", (viewModel.state.value as UiState.Error).message)
    }
}
