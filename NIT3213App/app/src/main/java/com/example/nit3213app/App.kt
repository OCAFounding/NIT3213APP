package com.example.nit3213app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class for NIT3213App
 * 
 * This class serves as the entry point for Hilt dependency injection.
 * The @HiltAndroidApp annotation tells Hilt to generate the necessary
 * dependency injection components at compile time.
 * 
 * Key Concepts:
 * - Hilt generates a base class that extends Application
 * - All dependency injection setup happens automatically
 * - This class must be declared in AndroidManifest.xml
 * 
 * Assignment Requirements:
 * - Implements dependency injection using Hilt framework
 * - Provides application-level dependencies
 * - Enables dependency injection throughout the app
 */
@HiltAndroidApp
class App : Application() {
    
    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects have been created.
     * 
     * This is where you would typically initialize application-level components,
     * but with Hilt, most initialization is handled automatically.
     */
    override fun onCreate() {
        super.onCreate()
        
        // Hilt automatically initializes dependency injection
        // No manual setup required here
    }
}
