package com.rhino.dailymotivation.security

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DeviceAuthorizationManager(
    private val context: Context
) {
    private val keyStoreManager = KeyStoreManager(context)
    private val secureStorageManager = SecureStorageManager(context)

    private fun buildChallenge(): ByteArray {
        val seed = "com.rhino.dailymotivation::device::binding::v1"
        return seed.toByteArray(Charsets.UTF_8)
    }

    fun initializeAndAuthorize(): Boolean {
        return try {
            if (!keyStoreManager.ensureKey()) {
                return false
            }

            val challenge = buildChallenge()
            val encodedChallenge = String(challenge, Charsets.UTF_8)

            val authorized = isDeviceAuthorized()
            if (authorized) {
                return true
            }

            val signature = keyStoreManager.signChallenge(challenge)
            val encodedSignature = keyStoreManager.encodeSignature(signature)
            val activationLabel = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).format(Date())
            secureStorageManager.saveAuthorization(encodedChallenge, encodedSignature, activationLabel)
            isDeviceAuthorized()
        } catch (exception: Exception) {
            false
        }
    }

    fun isDeviceAuthorized(): Boolean {
        return try {
            if (!keyStoreManager.hasKey()) return false

            val (storedChallenge, storedSignature, _) = secureStorageManager.loadAuthorization()
            if (storedChallenge.isNullOrBlank() || storedSignature.isNullOrBlank()) {
                return false
            }

            val challengeBytes = storedChallenge.toByteArray(Charsets.UTF_8)
            val signatureBytes = keyStoreManager.decodeSignature(storedSignature)
            keyStoreManager.verifySignature(challengeBytes, signatureBytes)
        } catch (exception: Exception) {
            false
        }
    }

    fun isDeviceAuthorizedState(): Boolean = isDeviceAuthorized()
}
