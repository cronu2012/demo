package com.rabbitmq.demo.utils;

import cn.hutool.core.util.NumberUtil;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

import static com.rabbitmq.demo.utils.JavaJsonConvert.java2Json;
import static com.rabbitmq.demo.utils.JavaJsonConvert.json2Java;


/**
 * @Author:MH
 */
@Slf4j
public class AppleUtil {

    public static void main(String... args){
        String key = "G3RSYCPkxyG4Kun1akNyktmkUdXOt1ewMnQJOgdxodVToQWN3eBKcFqAqlht6XT7M8MiGWC9owdonrMdq9LgnOarjifacbryy7nQPvhtEfGDEHfR9IbDZWGJdUllgisy";

        Map<String , String> paramMap = new TreeMap<>();
        paramMap.put("ifCode","pqpay");
        paramMap.put("amount","10000");
        paramMap.put("payOrderId","P1866324159519862786");
        paramMap.put("mchOrderNo","112269");
        paramMap.put("subject","subject");
        paramMap.put("wayCode","QRCODE");
//        paramMap.put("sign","DFFB8DBF4AB9ED5CFDEE90988B0B881C");
        paramMap.put("reqTime","1733802557752");
        paramMap.put("body","body");
        paramMap.put("createdAt","1733801328912");
        paramMap.put("appId","6756d9afe4b002bb05d18198");
        paramMap.put("clientIp","18.162.174.38");
        paramMap.put("currency","THB");
        paramMap.put("state","2");
        paramMap.put("mchNo","M1733745071");


        String paramSign = AppleUtil.convertMapToQueryStringIgnoreEmpty(paramMap) + "&key=" + key  ;
        String sign = MD5Encoder.encode(paramSign, "UTF-8").toUpperCase();

        log.info("paramMap:{}", paramMap);
        log.info("paramSign:{}", paramSign);
        log.info("sign:{}", sign);
    }

    /**
     * 常量0,进行中
     */
    public static final String ZERO = "0";
    /**
     * 常量1，成功
     */
    public static final String ONE = "1";
    /**
     * 常量2，失败
     */
    public static final String TWO = "2";

    public static final String DEFAULT_CHARSET = "UTF-8";
    /**
     * 签名算法
     */
    public static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    public static final String STATUS = "status";

    public static final String MESSAGE = "message";


    /**
     * java对象转换
     *
     */
    public static <T> T java2Map(Object entity, Class<T> clazz) {
        String json = java2Json(entity);
        return json2Java(json, clazz);
    }

    /**
     * 将分转换为带有小数点的元单位
     *
     * @param money 分
     * @param digit 小數位
     */
    public static String getMoney(String money, int digit) {
        return String.valueOf(NumberUtil.div(money, "100", digit));
    }

    /**
     * 将分转为不带小数点的元单位
     *
     * @param money 分
     */
    public static String getMoney(String money) {
        return String.valueOf(CompuUtil.divide(String.valueOf(money), "100").intValue());
    }

    /**
     * 将有小數點元單位转为不带小数点的分单位
     * ex: 100.00 转为 10000
     *
     * @param money  小數點元
     *
     */
    public static String getPointMoney(String money) {
        return String.valueOf(CompuUtil.multiply(String.valueOf(money), "100").intValue());
    }

    /**
     * Map转String；
     *
     */
    public static String getParam(Map<String, Object> paramMap) {
        String parameter = paramMap.toString().replace(", ", "&");
        parameter = parameter.substring(1, parameter.length() - 1);
        return parameter;
    }

    /**
     * 将实体类转换为待验签字符串（非空字段）
     *
     * @param object 实体类
     * @param remove 移除字段
     */
    public static StringBuffer getParamSign(Object object, String remove) {
        TreeMap<String, String> treeMap = java2Map(object, TreeMap.class);
        treeMap.remove(remove);
        StringBuffer sb = new StringBuffer();
        for (String key : treeMap.keySet()) {
            String value = treeMap.get(key);
            if (Validate.isNotNull(value)) {
                sb.append(key).append("=").append(value).append("&");
            }
        }
        return sb.deleteCharAt(sb.length() - 1);
    }

    /**
     * 将实体类转换为待验签字符串（包含空字段）
     *
     * @param object 实体类
     * @param remove 移除字段
     */
    public static StringBuffer getParamSignWithNull(Object object, String remove) {
        TreeMap<String, String> treeMap = java2Map(object, TreeMap.class);
        treeMap.remove(remove);
        StringBuffer sb = new StringBuffer();
        for (String key : treeMap.keySet()) {
            String value = treeMap.get(key);
            if (Validate.isNotNull(value)) {
                sb.append(key).append("=").append(value).append("&");
            } else {
                sb.append(key).append("=").append("&");
            }
        }
        return sb.deleteCharAt(sb.length() - 1);
    }

    /**
     * 将实体类转换为待验签字符串（非空字段）
     *
     * @param object  实体类
     * @param removes 移除多个字段
     * @return
     */
    public static StringBuffer getParamSign(Object object, String... removes) {
        TreeMap<String, String> treeMap = java2Map(object, TreeMap.class);
        for (String remove : removes) {
            treeMap.remove(remove.trim());
        }
        StringBuffer sb = new StringBuffer();
        for (String key : treeMap.keySet()) {
            String value = treeMap.get(key);
            if (Validate.isNotNull(value)) {
                sb.append(key).append("=").append(value).append("&");
            }
        }
        return sb.deleteCharAt(sb.length() - 1);
    }

    /**
     * 将实体类转换为待验签字符串（包含空字段）
     *
     * @param object  实体类
     * @param removes 移除多个字段
     * @return
     */
    public static StringBuffer getParamSignWithNull(Object object, String... removes) {
        TreeMap<String, String> treeMap = java2Map(object, TreeMap.class);
        for (String remove : removes) {
            treeMap.remove(remove.trim());
        }
        StringBuffer sb = new StringBuffer();
        for (String key : treeMap.keySet()) {
            String value = treeMap.get(key);
            if (Validate.isNotNull(value)) {
                sb.append(key).append("=").append(value).append("&");
            } else {
                sb.append(key).append("=").append("&");
            }
        }
        return sb.deleteCharAt(sb.length() - 1);
    }

    /**
     * 将实体类转换为待验签字符串（非空字段）(字符串没有&符号)
     *
     * @param object  实体类
     * @param removes 移除多个字段
     * @return
     */
    public static StringBuffer getParamSignWithEqualSymbol(Object object, String... removes) {
        TreeMap<String, String> treeMap = java2Map(object, TreeMap.class);
        for (String remove : removes) {
            treeMap.remove(remove.trim());
        }
        StringBuffer sb = new StringBuffer();
        for (String key : treeMap.keySet()) {
            String value = treeMap.get(key);
            if (Validate.isNotNull(value)) {
                sb.append(key).append("=").append(value);
            }
        }
        return sb;
    }
    /**
     * 将实体类转换为待验签字符串（非空字段），value1+value2...
     *
     * @param object  实体类
     * @param removes 移除多个字段
     * @return
     */
    public static StringBuffer getParamSignWithoutKeyAndEqualSymbol(Object object, String... removes) {
        TreeMap<String, String> treeMap = java2Map(object, TreeMap.class);
        for (String remove : removes) {
            treeMap.remove(remove.trim());
        }
        StringBuffer sb = new StringBuffer();
        for (String key : treeMap.keySet()) {
            String value = treeMap.get(key);
            if (Validate.isNotNull(value)) {
                sb.append(value);
            }
        }
        return sb;
    }
    /**
     * 将实体类转换为待验签字符串（非空字段），自定排序
     *
     * @param object  实体类
     * @param removes 移除多个字段
     * @return
     */
    public static StringBuffer getParamSignSortMyOrder(Object object, String... removes) {
        Map<String, String> linkedHashMap = java2Map(object, LinkedHashMap.class);
        for (String remove : removes) {
            linkedHashMap.remove(remove.trim());
        }
        StringBuffer sb = new StringBuffer();
        for (String key : linkedHashMap.keySet()) {
            String value = linkedHashMap.get(key);
            if (Validate.isNotNull(value)) {
                sb.append(key).append("=").append(value).append("&");
            }
        }
        return sb.deleteCharAt(sb.length() - 1);
    }


    /**
     * RSA签名
     *
     * @param content    待签名数据
     * @param privateKey 商户私钥
     * @return 签名值
     */
    public static String sign(String content, String privateKey) {
        try {
            log.info("待签名字符串：" + content);
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));

            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));

            byte[] signed = signature.sign();

            return Base64.getEncoder().encodeToString(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 签名
     *
     * @param params
     * @return
     * @throws Exception
     */
    public static String sign(TreeMap<String, String> params, String privateKey) throws Exception {
        String content = getSignContent(params);
        return sign(content, privateKey);
    }

    public static String getSignContent(TreeMap<String, String> params) {
        //签名明文组装不包含sign字段和signType
        params.remove("signMsg");

        if (params.containsKey("signType")) {
            //签名明文组装不包含sign字段和signType
            params.remove("signType");
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getValue() != null && entry.getValue().length() > 0) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        //String sign = md5(sb.toString().getBytes("UTF-8"));//记得是md5编码的加签
        return sb.toString();
    }

    public static boolean doCheck(TreeMap<String, String> params, String sign, String publicKey) {
        String content = getSignContent(params);
        return doCheck(content, sign, publicKey);
    }

    /**
     * RSA验签名检查
     *
     * @param content   待签名数据
     * @param sign      签名值
     * @param publicKey 分配给开发商公钥
     * @return 布尔值
     */
    public static boolean doCheck(String content, String sign, String publicKey) {
        try {
            log.info("RSA验签名检查" + content);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = Base64.getDecoder().decode(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));


            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);

            signature.initVerify(pubKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));

            return signature.verify(Base64.getDecoder().decode(sign));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static StringBuffer getNotNullValue(Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        for (String value : map.values()) {
            sb.append(value);
        }
        return sb;
    }

    public static StringBuffer getNotNullValue(Map<String, String> map, String division) {
        StringBuffer sb = new StringBuffer();
        for (String value : map.values()) {
            sb.append(value).append(division);
        }
        return sb.deleteCharAt(sb.length() - 1);
    }

    public static String getParamOnlyValue(Map<String, String> map){
        List<String> params = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()){
            String value = entry.getValue();
            if (Validate.isNotNull(value)){
                params.add(value);
            }
        }
        Collections.sort(params);
        for (String str : params){
            sb.append(str);
        }
        return sb.toString();
    }

    public static StringBuffer getParamValue(Map<String, String> map){
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : map.entrySet()){
            String value = entry.getValue();
            if (Validate.isNotNull(value)){
                sb.append(value);
            }
        }
        return sb;
    }

    /**
     * 获取隨機字串
     * @param object
     * @param params
     * @return
     */
    public static String getRandomString(Object object, String params){
        if (StringUtils.isNotEmpty(params))
            return String.valueOf(getStringRandom(Integer.parseInt(params)));
        return "";
    }
    public static String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        //參數length，表示生成幾位隨機數
        for(int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //輸出字母還是數字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //輸出是大寫字母還是小寫字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(26) + temp);
            } else {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    /**
     * 将实体类转换为待验签字符串（非空字段）（照字典序排序）(自定義加入符號)
     *
     * @param object 实体类
     * @param symbol 自定義符號
     * @return
     */
    public static StringBuffer getParamSignCustomizeSymbol(Object object, String symbol) {
        TreeMap<String, String> treeMap = java2Map(object, TreeMap.class);
        StringBuffer sb = new StringBuffer();
        for (String key : treeMap.keySet()) {
            String value = treeMap.get(key);
            if (Validate.isNotNull(value)) {
                sb.append(key).append("=").append(value).append(symbol);
            }
        }
        return sb.deleteCharAt(sb.length() - 1);
    }

    /**
     * 将实体类转换为待验签字符串（非空字段）（照字典序排序）(自定義加入符號)(無參數名)
     *
     * @param object 实体类
     * @param symbol 自定義符號
     * @return
     */
    public static StringBuffer getParamSignCustomizeSymbolNoKey(Object object, String symbol) {
        TreeMap<String, String> treeMap = java2Map(object, TreeMap.class);
        StringBuffer sb = new StringBuffer();
        for (String key : treeMap.keySet()) {
            String value = treeMap.get(key);
            if (Validate.isNotNull(value)) {
                sb.append(value).append(symbol);
            }
        }
        return sb.deleteCharAt(sb.length() - 1);
    }

    /**
     * 将实体类转换为待验签字符串（非空字段）（自定義排序）(自定義加入符號)
     *
     * @param object 实体类
     * @param symbol 自定義符號
     * @return
     */
    public static StringBuffer getParamSignCustomizeSymbolMySort(Object object, String symbol) {
        LinkedHashMap<String, String> linkedHashMap = java2Map(object, LinkedHashMap.class);
        StringBuffer sb = new StringBuffer();
        for (String key : linkedHashMap.keySet()) {
            String value = linkedHashMap.get(key);
            if (Validate.isNotNull(value)) {
                sb.append(key).append("=").append(value).append(symbol);
            }
        }
        return sb.deleteCharAt(sb.length() - 1);
    }

    /**
     * 将实体类转换为待验签字符串（非空字段）（自定義排序）(自定義加入符號)(無參數名)
     *
     * @param object 实体类
     * @param symbol 自定義符號
     * @return
     */
    public static StringBuffer getParamSignCustomizeSymbolNoKeyMySort(Object object, String symbol) {
        LinkedHashMap<String, String> linkedHashMap = java2Map(object, LinkedHashMap.class);
        StringBuffer sb = new StringBuffer();
        for (String key : linkedHashMap.keySet()) {
            String value = linkedHashMap.get(key);
            if (Validate.isNotNull(value)) {
                sb.append(value).append(symbol);
            }
        }
        return sb.deleteCharAt(sb.length() - 1);
    }



    /**
     * Map To String (kay1=value2&kay2-value2..) 排空字段
     */
    public static String convertMapToQueryStringIgnoreEmpty(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (entry.getValue() == null || entry.getValue().toString().isEmpty()) {
                continue;
            }
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.toString();
    }

    /**
     * Map To String (kay1=value2&kay2-value2..) 包含空字段
     */
    public static String convertMapToQueryString(Map<?, ?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.toString();
    }

}
