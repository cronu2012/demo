package com.rabbitmq.demo.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class CryptoUtils {
    // AES-128-CBC 加密方法
    public static String encryptData(String data, String key) throws Exception {
        // 取出前16個字元，若不足則補空格
        String keyBytes = key.length() > 16 ? key.substring(0, 16) : String.format("%-16s", key);

        // 隨機生成初始化向量 (IV)
        byte[] iv = new byte[16];
        IvParameterSpec ivParams = new IvParameterSpec(iv);
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes.getBytes("UTF-8"), "AES");

        // 初始化加密工具
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParams);

        // 執行加密
        byte[] encryptedBytes = cipher.doFinal(data.getBytes("UTF-8"));

        // 將加密結果與 IV 合併並進行 Base64 編碼
        String encryptedData = Base64.getEncoder().encodeToString(iv) + Base64.getEncoder().encodeToString(encryptedBytes);
        return encryptedData;
    }

    private static String encrypt(String text, String algorithm) throws NoSuchAlgorithmException {
        if (text == null || text.length() == 0) {
            return null;
        }
        MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
        messageDigest.update(text.getBytes(StandardCharsets.UTF_8));
        byte[] byteBuffer = messageDigest.digest();

        StringBuilder sb = new StringBuilder();
        for (byte b : byteBuffer) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        String data = "sk_af246f0be07b253492db0caf2f2ff4146e3320428c12060c893ddcaa50c9be43";  // 需要加密的資料
        String key = "$2a$10$Bo0a1E.Xhza/R3ADj0OtIe";  // 加密的密鑰

        String paramSign = "actual_money=0.0000&service_charge=0.0000&shop_phone=z16&shop_sub_number=53443&sub_money=100.0000&sub_payment_number=009312220241022135652056&sub_state=1a8083ed378bddd8a294df69ba023460a3ce39a844190ca48068f4324b0d3312b";
        // 執行加密

        String sign = encrypt(paramSign, "SHA-256");
//        String encryptedData = encryptData(data, key);
        System.out.println("加密後的資料: " + sign);
        System.out.println(System.currentTimeMillis()/1000);
    }
}
