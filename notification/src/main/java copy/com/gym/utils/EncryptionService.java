package com.gym.utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class EncryptionService {

    @Value("${app.jwtSecret}")
    private String secretKey;

    public String encrypt(String data) throws Exception {
        Cipher cipher = getCipher(Cipher.ENCRYPT_MODE);

        byte[] encryptedBytes = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedData) throws Exception {
        Cipher cipher = getCipher(Cipher.DECRYPT_MODE);

        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    private Cipher getCipher(int cipherMode) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        Key secretKey = generateSecretKey();
        cipher.init(cipherMode, secretKey);
        return cipher;
    }

    private Key generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey;
    }
}