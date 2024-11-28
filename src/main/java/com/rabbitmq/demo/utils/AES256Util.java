package com.rabbitmq.demo.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AES256Util {

    public static void main(String[] args) {
        String s = "hello";
        String key = "a8ec90c269c3bd8812739a4d710bbb85";
        String iv = "edbeaacdb228dcac";

        byte[] res = AES256Util.encrypt(s.getBytes(), key, iv);
        String encodedText = new String(Base64.getEncoder().encode(res));
        System.out.println(encodedText);
        byte[] decodedBytes = Base64.getDecoder().decode(encodedText);
        System.out.println(new String(AES256Util.decrypt(decodedBytes, key, iv)));
    }

    public static byte[] encrypt(byte[] data, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, stringToKey(key), stringToIv(iv));
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] decrypt(byte[] data, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, stringToKey(key), stringToIv(iv));
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static SecretKeySpec stringToKey(String key) {
        return new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
    }

    private static IvParameterSpec stringToIv(String iv) {
        return new IvParameterSpec(iv.getBytes());
    }

}
