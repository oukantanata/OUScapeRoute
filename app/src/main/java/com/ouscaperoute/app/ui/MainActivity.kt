package com.ouscaperoute.app.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ouscaperoute.app.databinding.ActivityMainBinding
import com.ouscaperoute.app.util.SecurePreferencesManager
import kotlinx.coroutines.launch

/**
 * MainActivity - Main entry point of the app
 * Shows game hub as primary display
 */
class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private lateinit var securePreferencesManager: SecurePreferencesManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        securePreferencesManager = SecurePreferencesManager(this)
        
        setupUI()
        setupListeners()
        
        // Check if accounts should be cleared (auto-logout on background)
        if (securePreferencesManager.getAutoLogoutOnBackground()) {
            clearSocialMediaAccounts()
        }
    }
    
    private fun setupUI() {
        // Set up the main UI with game hub display
        binding.apply {
            // Game selection button
            playGamesBtn.setOnClickListener {
                startActivity(Intent(this@MainActivity, GameSelectionActivity::class.java))
            }
            
            // Social media login button
            loginSocialMediaBtn.setOnClickListener {
                startActivity(Intent(this@MainActivity, SocialMediaLoginActivity::class.java))
            }
            
            // Settings button
            settingsBtn.setOnClickListener {
                startActivity(Intent(this@MainActivity, SettingsActivity::class.java))
            }
            
            // App Store button
            appStoreBtn.setOnClickListener {
                startActivity(Intent(this@MainActivity, AppStoreActivity::class.java))
            }
        }
    }
    
    private fun setupListeners() {
        // Can add additional listeners here
    }
    
    private fun clearSocialMediaAccounts() {
        lifecycleScope.launch {
            securePreferencesManager.clearAllData()
        }
    }
    
    override fun onResume() {
        super.onResume()
        // Save session time
        securePreferencesManager.saveLastSessionTime(System.currentTimeMillis())
    }
}
