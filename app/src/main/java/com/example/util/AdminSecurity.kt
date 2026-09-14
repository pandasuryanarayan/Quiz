package com.example.util

import java.security.MessageDigest

object AdminSecurity {
    /**
     * Cryptographic SHA-256 checksum for admin authentication.
     * Evaluated using a one-way digest to ensure plain text credentials
     * are never exposed or stored in code or memory.
     */
    private const val ADMIN_AUTH_DIGEST_HEX = "00e336c07cf58d4fbdfa49885b2d698c617a42f891b1770f1556a77d01a09951"

    /**
     * Verifies the entered password by hashing the input with SHA-256
     * and comparing against the cryptographic digest.
     */
    fun verifyPassword(candidate: String): Boolean {
        if (candidate.isEmpty()) return false
        return try {
            val messageDigest = MessageDigest.getInstance("SHA-256")
            val candidateHashBytes = messageDigest.digest(candidate.toByteArray(Charsets.UTF_8))
            val candidateHashHex = candidateHashBytes.joinToString("") { "%02x".format(it) }
            candidateHashHex.equals(ADMIN_AUTH_DIGEST_HEX, ignoreCase = true)
        } catch (_: Exception) {
            false
        }
    }
}
