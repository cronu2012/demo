package com.rabbitmq.demo.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;

public class AesTool {
    public static void main(String[] args) throws Exception {
        // API Key and Secret Key
        String apiKey = "825c850d-cc4b-410e-b3a4-b1fc3d898d79";
        String secretKey = "350cdd17-de40-43cc-9389-704db7cbd381";

        // Concatenated passphrase
        String passphrase = apiKey + secretKey;

        // Encrypted text (Base64 encoded, from CryptoJS)
        String encryptedBase64 = "U2FsdGVkX1/Lri6jfbFmJYrnvvvOyEsSG8DvP2HD5XlHlURby7+hTcd50CEXTHe4";

        // Decrypt
        String decryptedText = decrypt(encryptedBase64, passphrase);
        System.out.println("Decrypted Text: " + decryptedText);

        String signature = createSignature("nHUxQbHgEu", "VOBM7qzaRH",secretKey);
        System.out.println("signature Text: " + signature);
    }

    public static String decrypt(String encryptedBase64, String passphrase) throws Exception {
        // Decode Base64 input
        byte[] encryptedBytesWithSalt = Base64.getDecoder().decode(encryptedBase64);

        // Ensure the format is valid
        if (encryptedBytesWithSalt.length < 16) {
            throw new IllegalArgumentException("Invalid encrypted data format");
        }

        // Verify "Salted__" prefix
        byte[] saltedPrefix = Arrays.copyOfRange(encryptedBytesWithSalt, 0, 8);
        if (!new String(saltedPrefix, StandardCharsets.UTF_8).equals("Salted__")) {
            throw new IllegalArgumentException("Missing 'Salted__' prefix");
        }

        // Extract salt (Bytes 8-16)
        byte[] salt = Arrays.copyOfRange(encryptedBytesWithSalt, 8, 16);

        // Derive key and IV using OpenSSL EVP_BytesToKey method
        byte[][] keyAndIv = EVP_BytesToKey(32, 16, MessageDigest.getInstance("MD5"), salt, passphrase.getBytes(StandardCharsets.UTF_8), 1);
        SecretKeySpec secretKey = new SecretKeySpec(keyAndIv[0], "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(keyAndIv[1]);

        // Extract actual encrypted data (after 16 bytes of header)
        byte[] encryptedBytes = Arrays.copyOfRange(encryptedBytesWithSalt, 16, encryptedBytesWithSalt.length);

        // Ensure ciphertext length is a multiple of 16
        if (encryptedBytes.length % 16 != 0) {
            throw new IllegalArgumentException("Ciphertext length is not a multiple of 16 bytes");
        }

        // Initialize AES Cipher for CBC mode
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        // Perform decryption
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    // OpenSSL EVP_BytesToKey Key Derivation
    public static byte[][] EVP_BytesToKey(int keyLength, int ivLength, MessageDigest md, byte[] salt, byte[] data, int count) {
        byte[] key = new byte[keyLength];
        byte[] iv = new byte[ivLength];
        byte[] result = new byte[keyLength + ivLength];

        byte[] hash = null;
        byte[] previous = new byte[0];

        int offset = 0;
        while (offset < keyLength + ivLength) {
            md.update(previous);
            md.update(data);
            md.update(salt, 0, 8);
            hash = md.digest();

            for (int i = 1; i < count; i++) {
                hash = md.digest(hash);
            }

            int remaining = Math.min(hash.length, keyLength + ivLength - offset);
            System.arraycopy(hash, 0, result, offset, remaining);
            offset += remaining;

            previous = hash;
        }

        System.arraycopy(result, 0, key, 0, keyLength);
        System.arraycopy(result, keyLength, iv, 0, ivLength);

        return new byte[][]{key, iv};
    }


    public static String createSignature(String merchantId, String clientId, String secretKey) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder()
                .setSubject("Signature")
                .claim("merchantId", merchantId)
                .claim("clientId", clientId)
                .claim("iat", "1744193276499")
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
        return token;
    }

}
