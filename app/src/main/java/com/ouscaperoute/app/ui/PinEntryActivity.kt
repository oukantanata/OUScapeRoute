package com.ouscaperoute.app.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ouscaperoute.app.databinding.ActivityPinEntryBinding
import com.ouscaperoute.app.util.SecurePreferencesManager

/**
 * PinEntryActivity - Hidden PIN entry for accessing saved social media accounts
 * This appears as "Premium/Pro Access" in settings
 */
class PinEntryActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityPinEntryBinding
    private lateinit var securePreferencesManager: SecurePreferencesManager
    private var enteredPin = StringBuilder()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityPinEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        securePreferencesManager = SecurePreferencesManager(this)
        
        setupToolbar()
        setupNumberPad()
    }
    
    private fun setupToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }
    
    private fun setupNumberPad() {
        // Number buttons 0-9
        val numberButtons = listOf(
            binding.btn0, binding.btn1, binding.btn2, binding.btn3, binding.btn4,
            binding.btn5, binding.btn6, binding.btn7, binding.btn8, binding.btn9
        )
        
        numberButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                if (enteredPin.length < 6) { // Max 6 digits
                    enteredPin.append(index)
                    updatePinDisplay()
                }
            }
        }
        
        // Clear button
        binding.btnClear.setOnClickListener {
            enteredPin.clear()
            updatePinDisplay()
        }
        
        // Backspace button
        binding.btnBackspace.setOnClickListener {
            if (enteredPin.isNotEmpty()) {
                enteredPin.deleteCharAt(enteredPin.length - 1)
                updatePinDisplay()
            }
        }
        
        // Confirm button
        binding.btnConfirm.setOnClickListener {
            verifyPin()
        }
    }
    
    private fun updatePinDisplay() {
        binding.pinDisplay.text = "*".repeat(enteredPin.length)
    }
    
    private fun verifyPin() {
        val correctPin = securePreferencesManager.getAccessPin()
        val enteredPinStr = enteredPin.toString()
        
        if (enteredPinStr == correctPin) {
            Toast.makeText(this, "Access Granted!", Toast.LENGTH_SHORT).show()
            setResult(RESULT_OK)
            finish()
        } else {
            Toast.makeText(this, "Invalid PIN", Toast.LENGTH_SHORT).show()
            enteredPin.clear()
            updatePinDisplay()
        }
    }
    
    companion object {
        const val RESULT_OK = 1
    }
}
