package com.rhino.dailymotivation.security

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class SecureStorageManager(context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val preferences = EncryptedSharedPreferences.create(
        "secure_state",
        masterKey,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        private const val AUTH_CHALLENGE_KEY = "device_auth_challenge"
        private const val AUTH_SIGNATURE_KEY = "device_auth_signature"
        private const val AUTH_ACTIVATED_AT_KEY = "device_auth_activated_at"
        private const val THEME_KEY = "selected_theme"
        private const val LAST_MESSAGE_DATE_KEY = "last_message_date"
        private const val LAST_MESSAGE_TEXT_KEY = "last_message_text"
    }

    fun saveAuthorization(challenge: String, signature: String, activatedAt: String) {
        preferences.edit()
            .putString(AUTH_CHALLENGE_KEY, challenge)
            .putString(AUTH_SIGNATURE_KEY, signature)
            .putString(AUTH_ACTIVATED_AT_KEY, activatedAt)
            .apply()
    }

    fun loadAuthorization(): Triple<String?, String?, String?> {
        return Triple(
            preferences.getString(AUTH_CHALLENGE_KEY, null),
            preferences.getString(AUTH_SIGNATURE_KEY, null),
            preferences.getString(AUTH_ACTIVATED_AT_KEY, null)
        )
    }

    fun saveTheme(themeName: String) {
        preferences.edit().putString(THEME_KEY, themeName).apply()
    }

    fun loadTheme(): String = preferences.getString(THEME_KEY, "System") ?: "System"

    fun saveLastDailyMessage(dateKey: String, message: String) {
        preferences.edit()
            .putString(LAST_MESSAGE_DATE_KEY, dateKey)
            .putString(LAST_MESSAGE_TEXT_KEY, message)
            .apply()
    }

    fun loadLastDailyMessage(dateKey: String): String? {
        val storedDate = preferences.getString(LAST_MESSAGE_DATE_KEY, null)
        return if (storedDate == dateKey) {
            preferences.getString(LAST_MESSAGE_TEXT_KEY, null)
        } else null
    }

    fun clear() {
        preferences.edit().clear().apply()
    }
}
