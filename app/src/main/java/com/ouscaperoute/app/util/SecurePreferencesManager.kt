package com.ouscaperoute.app.util

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * Secure preference manager for storing encrypted data
 */
class SecurePreferencesManager(context: Context) {
    
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    
    private val encryptedSharedPreferences = EncryptedSharedPreferences.create(
        context,
        "ou_scape_route_secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    
    fun saveAccessPin(pin: String) {
        encryptedSharedPreferences.edit().putString("access_pin", pin).apply()
    }
    
    fun getAccessPin(): String {
        return encryptedSharedPreferences.getString("access_pin", "0000") ?: "0000"
    }
    
    fun saveToken(platform: String, token: String) {
        encryptedSharedPreferences.edit().putString("token_$platform", token).apply()
    }
    
    fun getToken(platform: String): String? {
        return encryptedSharedPreferences.getString("token_$platform", null)
    }
    
    fun deleteToken(platform: String) {
        encryptedSharedPreferences.edit().remove("token_$platform").apply()
    }
    
    fun saveLastSessionTime(time: Long) {
        encryptedSharedPreferences.edit().putLong("last_session_time", time).apply()
    }
    
    fun getLastSessionTime(): Long {
        return encryptedSharedPreferences.getLong("last_session_time", 0L)
    }
    
    fun saveAutoLogoutOnBackground(enabled: Boolean) {
        encryptedSharedPreferences.edit().putBoolean("auto_logout", enabled).apply()
    }
    
    fun getAutoLogoutOnBackground(): Boolean {
        return encryptedSharedPreferences.getBoolean("auto_logout", true)
    }
    
    fun clearAllData() {
        encryptedSharedPreferences.edit().clear().apply()
    }
}
