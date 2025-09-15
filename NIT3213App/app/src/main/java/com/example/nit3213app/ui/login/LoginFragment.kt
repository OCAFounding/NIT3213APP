package com.example.nit3213app.ui.login

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.nit3213app.R
import com.example.nit3213app.common.UiState
import com.example.nit3213app.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Login Fragment for NIT3213App
 * 
 * This fragment handles user authentication by providing a form for
 * campus selection, username, and password input. It communicates with
 * the LoginViewModel to perform authentication and navigate to the
 * dashboard on successful login.
 * 
 * Key Concepts:
 * - Fragment lifecycle and ViewBinding
 * - ViewModel communication
 * - State observation with Flow
 * - Navigation component usage
 * - Hilt dependency injection
 * 
 * Assignment Requirements:
 * - Campus selection spinner
 * - Username and password input fields
 * - Login button with validation
 * - Error message display
 * - Navigation to dashboard on success
 */
@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    // =============================================================================
    // PROPERTIES
    // =============================================================================
    
    /**
     * ViewModel for login logic and state management
     */
    private val viewModel: LoginViewModel by viewModels()
    
    /**
     * ViewBinding for type-safe view access
     */
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    
    /**
     * List of available campuses for selection
     */
    private val campuses = listOf("footscray", "sydney", "br")

    // =============================================================================
    // LIFECYCLE METHODS
    // =============================================================================
    
    /**
     * Called when the view is created
     * Sets up the UI and observes ViewModel state
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Initialize ViewBinding
        _binding = FragmentLoginBinding.bind(view)
        
        // Setup UI components
        setupCampusSpinner()
        setupLoginButton()
        observeViewModelState()
    }
    
    /**
     * Called when the view is destroyed
     * Cleans up ViewBinding to prevent memory leaks
     */
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // =============================================================================
    // UI SETUP METHODS
    // =============================================================================
    
    /**
     * Setup campus selection spinner
     */
    private fun setupCampusSpinner() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            campuses
        )
        binding.spinnerCampus.adapter = adapter
    }
    
    /**
     * Setup login button click listener
     */
    private fun setupLoginButton() {
        binding.btnLogin.setOnClickListener {
            // Get input values
            val campus = campuses[binding.spinnerCampus.selectedItemPosition]
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            
            // Call ViewModel to perform login
            viewModel.login(campus, username, password)
        }
    }
    
    /**
     * Observe ViewModel state changes
     */
    private fun observeViewModelState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is UiState.Idle -> renderIdleState()
                        is UiState.Loading -> renderLoadingState()
                        is UiState.Error -> renderErrorState(state.message)
                        is UiState.Success -> {
                            renderIdleState()
                            navigateToDashboard()
                        }
                    }
                }
            }
        }
    }

    // =============================================================================
    // UI STATE RENDERING METHODS
    // =============================================================================
    
    /**
     * Render idle state (initial state)
     */
    private fun renderIdleState() {
        binding.progress.visibility = View.GONE
        binding.tvError.visibility = View.GONE
        binding.btnLogin.isEnabled = true
    }
    
    /**
     * Render loading state
     */
    private fun renderLoadingState() {
        binding.progress.visibility = View.VISIBLE
        binding.tvError.visibility = View.GONE
        binding.btnLogin.isEnabled = false
    }
    
    /**
     * Render error state
     */
    private fun renderErrorState(message: String) {
        binding.progress.visibility = View.GONE
        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text = message
        binding.btnLogin.isEnabled = true
    }
    
    /**
     * Navigate to dashboard screen
     */
    private fun navigateToDashboard() {
        findNavController().navigate(R.id.action_login_to_dashboard)
    }
}
