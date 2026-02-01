package com.ionix.application.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DESEncryptionServiceTest {

    private DESEncryptionService encryptionService;

    @BeforeEach
    void setUp() {
        encryptionService = new DESEncryptionService("ionix123456");
    }

    @Test
    void testEncrypt_Success() {
        String plainText = "1-9";
        String encrypted = encryptionService.encrypt(plainText);

        assertNotNull(encrypted);
        assertFalse(encrypted.isEmpty());
        assertNotEquals(plainText, encrypted);
    }

    @Test
    void testEncrypt_DifferentInputs() {
        String text1 = "1-9";
        String text2 = "2-8";

        String encrypted1 = encryptionService.encrypt(text1);
        String encrypted2 = encryptionService.encrypt(text2);

        assertNotEquals(encrypted1, encrypted2);
    }

    @Test
    void testEncrypt_ConsistentEncryption() {
        String plainText = "1-9";
        String encrypted1 = encryptionService.encrypt(plainText);
        String encrypted2 = encryptionService.encrypt(plainText);

        assertEquals(encrypted1, encrypted2);
    }
}
