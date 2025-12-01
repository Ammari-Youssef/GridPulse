package com.youssef.GridPulse.domain.security.service;

import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    /**
     * Mock encryption method for portfolio/demo purposes.
     * In production, replace with real encryption logic (e.g., AES-256).
     */
    public String encrypt(String rawPrivateKey) {
        if (rawPrivateKey == null || rawPrivateKey.isBlank()) {
            throw new IllegalArgumentException("Private key cannot be null or blank");
        }

        // Simulate encryption by wrapping the key
        return "ENCRYPTED[" + rawPrivateKey.hashCode() + "]";
    }

    /**
     * A mock decrypt method for internal use only.
     * Unused for now. Envisagé pour l'avenir si il y en a :)
     */
    public String decrypt(String encryptedPrivateKey) {
        // This is just a placeholder — do not use in production
        return "DECRYPTED_CONTENT";
    }
}
