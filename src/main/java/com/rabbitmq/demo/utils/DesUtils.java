package com.rabbitmq.demo.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class DesUtils {
    public static String encrypt(String encryptString, String encryptKey, String myIv) throws Exception {
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, stringToIv(myIv));
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String decrypt(String decryptString, String decryptKey, String myIv) throws Exception {
        byte[] byteMi = Base64.getDecoder().decode(decryptString);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, stringToIv(myIv));
        byte[] decryptedData = cipher.doFinal(byteMi);
        return new String(decryptedData);
    }

    private static IvParameterSpec stringToIv(String iv){
        return new IvParameterSpec(iv.getBytes());
    }
}
