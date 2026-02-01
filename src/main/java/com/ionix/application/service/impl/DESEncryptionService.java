package com.ionix.application.service.impl;

import com.ionix.application.service.EncryptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
@Slf4j
public class DESEncryptionService implements EncryptionService {

    private final String encryptionKey;

    public DESEncryptionService(@Value("${app.encryption.des.key}") String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    @Override
    public String encrypt(String plainText) {
        try {
            log.debug("Encrypting text with DES");
            DESKeySpec keySpec = new DESKeySpec(encryptionKey.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            javax.crypto.SecretKey key = keyFactory.generateSecret(keySpec);
            
            byte[] cleartext = plainText.getBytes(StandardCharsets.UTF_8);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            
            byte[] encryptedBytes = cipher.doFinal(cleartext);
            String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
            
            log.debug("Text encrypted successfully");
            return encryptedText;
        } catch (Exception e) {
            log.error("Error encrypting text", e);
            throw new RuntimeException("Error during encryption", e);
        }
    }
}
