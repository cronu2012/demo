package com.rabbitmq.demo.spring.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class AesUtil {
    /**
     * Aes 加密
     *
     * @param data 需要加密的明文
     * @param key  秘钥 key 长度只能是 16 字节
     * @param iv   初始向量
     * @return byte
     */
    public static byte[] encryptAesGCM(byte[] data, String key, String iv) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"), new GCMParameterSpec(128, iv.getBytes(StandardCharsets.UTF_8)));
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Encrypt error");
        }
    }

    /**
     * Aes 解密
     *
     * @param data 需要解密的内容
     * @param key  秘钥 key 长度只能是 16 字节
     * @param iv   初始向量
     * @return byte
     */
    public static byte[] decryptAesGCM(byte[] data, String key, String iv) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"), new GCMParameterSpec(128, iv.getBytes(StandardCharsets.UTF_8)));
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Decrypt error");
        }
    }

    public static void main(String[] args) throws Exception {

        Map map = new HashMap();
        map.put("amount", "20000");
        map.put("clientIp", "185.177.125.118");
        map.put("createdAt", "1642670220706");
        map.put("currency", "INR");
        map.put("mchNo", "M164266560x");
        map.put("mchOrderNo", "202201201717009954494810");
        map.put("payAmount", "0");
        map.put("payOrderId", "Pocom76c665a2");
        map.put("reqTime", "1642670259876");
        map.put("state", "2");
        map.put("subject", "jinbi");
        String key = "lpppay7852314569";
        String iv = "gfd3dw234234ds1223224thy";
        //加密
        //AES加密
        byte[] encrypt = encryptAesGCM(JSONObject.toJSONString(map).getBytes(StandardCharsets.UTF_8), key, iv);
        //base64加密
        String encode = Base64.getEncoder().encodeToString(encrypt);
        System.out.println("加密数据："+encode);

        //解密
        //base64解密
        byte[] data =Base64.getDecoder().decode(encode);
        //AES解密
        byte[] plaintext = decryptAesGCM(data, key, iv);
        String result = StrUtil.str(plaintext, StandardCharsets.UTF_8);
        System.out.println("解密数据："+ result);

        JSONObject res = JSON.parseObject(result);
        String orderId = res.getString("mchOrderNo");

        System.out.println("訂單號："+ orderId);
    }
}
