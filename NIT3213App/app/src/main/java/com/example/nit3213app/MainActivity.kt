package com.example.nit3213app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * MainActivity for NIT3213App
 * 
 * This is the single activity in the app that hosts the Navigation component.
 * It follows the single-activity architecture pattern where all navigation
 * happens through fragments managed by the Navigation component.
 * 
 * Key Concepts:
 * - Single-activity architecture
 * - Navigation component host
 * - Hilt dependency injection
 * - Fragment-based navigation
 * 
 * Assignment Requirements:
 * - Single activity hosting Navigation component
 * - Uses Hilt for dependency injection
 * - Hosts all three screens (Login, Dashboard, Details)
 * - Follows modern Android architecture patterns
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    
    /**
     * Called when the activity is first created
     * 
     * Sets up the activity with the main layout that contains
     * the Navigation component for fragment navigation.
     * 
     * @param savedInstanceState Previously saved state (null for first creation)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Set the layout that contains the Navigation component
        setContentView(R.layout.activity_main)
    }
}
