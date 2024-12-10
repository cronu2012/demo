package com.rabbitmq.demo.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPObject;
import com.rabbitmq.tools.json.JSONUtil;

import java.net.URLEncoder;
import java.util.*;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

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

        validateSign("123321","1732541832", "");

        /**
         * {mchNo=M111,reqTime=1725637496635,
         *  sign=74411B9F5F3857DAE20FC780E6D589BE, signType=MD5,
         *  version=2.0}
         */
        TreeMap params = new TreeMap();
        params.put("mchNo", "M111");
        params.put("reqTime", "1725637496635");
        params.put("signType", "MD5");
        params.put("version", "2.0");
        params.put("sign", "02F88DAD45D54745FCE309F56DA0126C");

        String key = "d7c540d43a69ae8f";
        String iv = "111";

        //加密
        //AES加密
        byte[] encrypt = encryptAesGCM(JSONObject.toJSONString(params).getBytes(StandardCharsets.UTF_8), key, iv);
        //base64加密
        String encode = Base64.getEncoder().encodeToString(encrypt);

        System.out.println("加密数据："+encode);
        LinkedHashMap request = new LinkedHashMap<>();
        request.put("key", "TTExMQ==");
        request.put("data", encode);
        System.out.println("加密請求："+ JSON.toJSONString(request));

        //解密
        //base64解密
        byte[] data =Base64.getDecoder().decode(encode);
//        AES解密
        byte[] plaintext = decryptAesGCM(data, key, iv);
        String result = StrUtil.str(plaintext, StandardCharsets.UTF_8);
        System.out.println("解密数据："+ result);

    }


    private static void validateSign(String merchantNo, String timestamp, String sign) throws Exception {
//        LambdaQueryWrapper<Merchant> qw = new LambdaQueryWrapper<>();
//        qw.select(Merchant::getKey)
//                .eq(Merchant::getMerchantNo, merchantNo).
//                last(" limit 1");
//        Merchant merchant = merchantDao.selectOne(qw);

        String secret = "9d21c34fe8dd1e30bb4e7237f7f131cb";
        String paramSign = timestamp + "\n" + secret;
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(paramSign.getBytes(StandardCharsets.UTF_8));
        String stringOfSigned = URLEncoder.encode(new String(Base64.getEncoder().encode(signData)), "UTF-8");
        System.out.println(stringOfSigned);
    }
}
