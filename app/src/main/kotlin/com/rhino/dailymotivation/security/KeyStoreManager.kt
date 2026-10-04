package com.rhino.dailymotivation.security

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.PrivateKey
import java.security.PublicKey
import java.security.Signature

class KeyStoreManager(private val context: Context) {
    companion object {
        const val KEY_ALIAS = "daily_motivation_device_auth_key"
    }

    private val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    fun ensureKey(): Boolean {
        return if (keyStore.containsAlias(KEY_ALIAS)) {
            true
        } else {
            try {
                val generator = KeyPairGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_RSA,
                    "AndroidKeyStore"
                )

                val spec = KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_SIGN or KeyProperties.PURPOSE_VERIFY
                )
                    .setDigests(KeyProperties.DIGEST_SHA256)
                    .setKeySize(2048)
                    .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
                    .setUserAuthenticationRequired(false)
                    .build()

                generator.initialize(spec)
                generator.generateKeyPair()
                true
            } catch (exception: Exception) {
                false
            }
        }
    }

    fun hasKey(): Boolean = keyStore.containsAlias(KEY_ALIAS)

    fun signChallenge(challenge: ByteArray): ByteArray {
        val privateKey = privateKey() ?: throw SecurityException("Private key was not available")
        val signer = Signature.getInstance("SHA256withRSA")
        signer.initSign(privateKey)
        signer.update(challenge)
        return signer.sign()
    }

    fun verifySignature(challenge: ByteArray, signature: ByteArray): Boolean {
        val publicKey = publicKey() ?: return false
        val verifier = Signature.getInstance("SHA256withRSA")
        verifier.initVerify(publicKey)
        verifier.update(challenge)
        return verifier.verify(signature)
    }

    fun encodeSignature(signature: ByteArray): String =
        Base64.encodeToString(signature, Base64.NO_WRAP)

    fun decodeSignature(value: String): ByteArray =
        Base64.decode(value, Base64.NO_WRAP)

    private fun privateKey(): PrivateKey? {
        return if (keyStore.containsAlias(KEY_ALIAS)) {
            val privateKeyEntry = keyStore.getEntry(KEY_ALIAS, null) as? KeyStore.PrivateKeyEntry
            privateKeyEntry?.privateKey
        } else {
            null
        }
    }

    private fun publicKey(): PublicKey? {
        return if (keyStore.containsAlias(KEY_ALIAS)) {
            val privateKeyEntry = keyStore.getEntry(KEY_ALIAS, null) as? KeyStore.PrivateKeyEntry
            privateKeyEntry?.certificate?.publicKey
        } else {
            null
        }
    }
}
