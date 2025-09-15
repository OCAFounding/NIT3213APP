package com.example.nit3213app.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.nit3213app.R
import com.example.nit3213app.common.UiState
import com.example.nit3213app.databinding.FragmentDashboardBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Dashboard Fragment for NIT3213App
 * 
 * This fragment displays the dashboard with a list of entities from the API.
 * It shows the total count and allows users to click on entities to view details.
 * 
 * Key Concepts:
 * - Fragment lifecycle and ViewBinding
 * - ViewModel communication
 * - State observation with Flow
 * - RecyclerView with adapter
 * - Navigation component usage
 * - Hilt dependency injection
 * 
 * Assignment Requirements:
 * - Displays total entity count
 * - Shows entity list in RecyclerView
 * - Handles item clicks for navigation
 * - Loading and error state management
 * - Navigation to details screen
 */
@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    // =============================================================================
    // PROPERTIES
    // =============================================================================
    
    /**
     * ViewModel for dashboard logic and state management
     */
    private val viewModel: DashboardViewModel by viewModels()
    
    /**
     * ViewBinding for type-safe view access
     */
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    
    /**
     * RecyclerView adapter for entity list
     */
    private lateinit var adapter: EntityAdapter

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
        _binding = FragmentDashboardBinding.bind(view)
        
        // Setup UI components
        setupRecyclerView()
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
     * Setup RecyclerView with adapter
     */
    private fun setupRecyclerView() {
        adapter = EntityAdapter { entity ->
            // Navigate to details screen with selected entity
            val action = DashboardFragmentDirections.actionDashboardToDetails(entity)
            findNavController().navigate(action)
        }
        binding.recycler.adapter = adapter
    }
    
    /**
     * Observe ViewModel state changes
     */
    private fun observeViewModelState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is UiState.Loading -> renderLoadingState()
                        is UiState.Error -> renderErrorState(state.message)
                        is UiState.Success -> renderSuccessState(state.data)
                        is UiState.Idle -> Unit // No action needed for idle state
                    }
                }
            }
        }
    }

    // =============================================================================
    // UI STATE RENDERING METHODS
    // =============================================================================
    
    /**
     * Render loading state
     */
    private fun renderLoadingState() {
        binding.progress.visibility = View.VISIBLE
        binding.tvError.visibility = View.GONE
        binding.tvTotal.text = "Loading..."
    }
    
    /**
     * Render error state
     */
    private fun renderErrorState(message: String) {
        binding.progress.visibility = View.GONE
        binding.tvError.visibility = View.VISIBLE
        binding.tvError.text = message
        binding.tvTotal.text = "Total: 0"
        adapter.submitList(emptyList())
    }
    
    /**
     * Render success state with data
     */
    private fun renderSuccessState(data: com.example.nit3213app.domain.model.DashboardData) {
        binding.progress.visibility = View.GONE
        binding.tvError.visibility = View.GONE
        binding.tvTotal.text = "Total: ${data.entityTotal}"
        adapter.submitList(data.entities)
    }
}
