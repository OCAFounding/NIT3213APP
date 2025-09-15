package com.example.nit3213app.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.nit3213app.R
import com.example.nit3213app.databinding.FragmentDetailsBinding

/**
 * Details Fragment for NIT3213App
 * 
 * This fragment displays detailed information about a selected entity.
 * It receives the entity data through Navigation Safe Args and displays
 * all properties including the description.
 * 
 * Key Concepts:
 * - Fragment lifecycle and ViewBinding
 * - Navigation Safe Args for type-safe navigation
 * - Data display and formatting
 * - User-friendly layout
 * 
 * Assignment Requirements:
 * - Displays all entity information
 * - Shows property1, property2, and description
 * - Receives data via Navigation Safe Args
 * - User-friendly layout design
 */
class DetailsFragment : Fragment(R.layout.fragment_details) {

    // =============================================================================
    // PROPERTIES
    // =============================================================================
    
    /**
     * ViewBinding for type-safe view access
     */
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    
    /**
     * Navigation arguments containing the selected entity
     */
    private val args: DetailsFragmentArgs by navArgs()

    // =============================================================================
    // LIFECYCLE METHODS
    // =============================================================================
    
    /**
     * Called when the view is created
     * Sets up the UI with entity data
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        // Initialize ViewBinding
        _binding = FragmentDetailsBinding.bind(view)
        
        // Display entity data
        displayEntityData()
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
     * Display entity data in the UI
     */
    private fun displayEntityData() {
        val entity = args.entity
        
        // Set entity properties to TextViews
        binding.tvProp1.text = entity.property1
        binding.tvProp2.text = entity.property2
        binding.tvDescription.text = entity.description
    }
}
