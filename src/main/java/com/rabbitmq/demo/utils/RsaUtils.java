package com.rabbitmq.demo.utils;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

@Slf4j
public class RsaUtils {
    public static final String CHARSET = "UTF-8";
    //密钥算法
    public static final String ALGORITHM_RSA = "RSA";
    //RSA 签名算法
    public static final String ALGORITHM_RSA_SIGN = "SHA256WithRSA";
    public static final int ALGORITHM_RSA_PRIVATE_KEY_LENGTH = 2048;

    private static String dataStr = "currency=THB&merchantNo=XP1059&merchantOrderNo=23621341&orderAmount=700.00&orderNo=237733&orderStatus=SUCCESS&payModel=PAYOUT";
    private static String key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC3FUdyLwgtik8YFcb235WQd3TEu8DeO9xK53uQ3o4h3XcUWFRJYGoMc0/IQ74qp7j3FAH9AUml1g8T6BF9on+n/Ys3y8ojqGx5OSWQ/3LW70IVuTVUKa5w/0kbeefyfLUR9rBrTDZ1RFFHTB7whmZDxyw33Nw6XVsTwg8jRoG801I6P4RE8H7YwxnCe2m3x9WnZnOBZDLR575z6slFe3TwJTPR1IVFjeD2X0++gyeTc+7t7ehEFIQ1YLYU0c4ntZhTzaNU9uwm6k4cdBv7LxLBgDj6dCOfexvHeHlu2HSbqKlzuzq0r57wPSWht7mcotIX50ngoaq/ZxMWhQ/0wP81AgMBAAECggEAYwEu0aB9W6MfgnbEUVw3FRiEHJ8rfQIB7r+fIog3dDi+3FGnwsZQkssIqdN4UrjAXVyEdrzrhrJrFOi6DKaDQeTqFBIwDXhWsHcmyXJaN8DKi9TL8edYXeKvNhyGQasOf9hXLq7YBDIdgeC4GnmTc7ORReQ5c5ZoWhbm+Lx4DBOPuvNDl5Al3vwJlg32px+ytjgfaxN2ObGjkuxSiGsWm2PmDPvexPMNUBAPI8oAwKWhfLpY+5vPhbbU5tafUPVkQJBkAQgPnlGc469ysqlKeuvnSO+x1wvBg5KcQyLL5AJgfRhZwhAgC03FzvCKf2Tz4T46RGjxKkfIu9sRLOpjQQKBgQDrc49cvYlZzpEEH4DxoIK6l9qRZgqKOh0gnqKFYevtVDF4MWqgxexOFyvWunQuwOhjpB1NLhab+0JczjasT7B27B892Cw/rGddlKa7KcHR0pvRkOaJX0Kc11GHpDQY15M8r5YyaYYvJ7I1alKh/Z88XYVxqw9qZdHf1vx6xs95DwKBgQDHD7XUAU7kcHdeM/ikyCubp1WjNels3GL3Bkl/QrJTNOX4EPhtkLkigodFU2SUqDLEQAVh8tJ8Ykr/8+LcNmlMAmdI0H8yuzmVayp8bOerO3kErP+2oj8Flqqzr5qoOlR/JYLUjlsA3SGGfQqHRIWzCh05ntZmlQokFNvRkV7bewKBgCgsSXO7ayoFkryvW2Lezi9kJYjVbkrza3DbYEN917FgwigN1nGkE5OnZHV4zsYDaCNIJy+6A2WN017eWHnCBwvjcdktfy9GDUTsTh51G3rfgnKZVJxvwn/cRS7tl239qkOaefgUBS8aiM+QWpUFHxw/oLsqQrEWul6E4gNY1KGFAoGBAJ72Nb6SON0Fzt0EZae5TdGkBCgskjZjnAgmFiUxLtRxZr/9VVWxf5ZGZB2BZc2sr7W2ZlX8ogfAOqJAsUYnkViohrBIB3uMtYRTqv7nvO0ptg9800uWLvuYgMKzFa8HvZg3bU2uTI/ZBr+uNO0mVBpQllwAMriChjGgEpY9H2HnAoGADWOUXQfHPf+6vRJDaMtp05EHgR9XDf8OMMIA5YCk/ec1hjSkv/hK/D+S5Hq5LXuBVd+YEM9A/kZw/8hC918xEDOmF8O5TiFFZUMuTdVvYXsNDmMd6YwCaJvyQfmZXvcSUXmeKvImwc+Ytl4tDdG+0xksZEyqg4GYN3PhnxgfcDU=";


    /**
     * 初始化RSA算法密钥对
     *
     * @param keysize RSA1024已经不安全了,建议2048
     * @return 经过Base64编码后的公私钥Map, 键名分别为publicKey和privateKey
     */
    public static Map<String, String> initRSAKey(int keysize) {
        if (keysize != ALGORITHM_RSA_PRIVATE_KEY_LENGTH) {
            throw new IllegalArgumentException("RSA1024已经不安全了,请使用" + ALGORITHM_RSA_PRIVATE_KEY_LENGTH + "初始化RSA密钥对");
        }
        //为RSA算法创建一个KeyPairGenerator对象
        KeyPairGenerator kpg;
        try {
            kpg = KeyPairGenerator.getInstance(ALGORITHM_RSA);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + ALGORITHM_RSA + "]");
        }
        //初始化KeyPairGenerator对象,不要被initialize()源码表面上欺骗,其实这里声明的size是生效的
        kpg.initialize(ALGORITHM_RSA_PRIVATE_KEY_LENGTH);
        //生成密匙对
        KeyPair keyPair = kpg.generateKeyPair();
        //得到公钥
        Key publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        //得到私钥
        Key privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        Map<String, String> keyPairMap = new HashMap<String, String>();
        keyPairMap.put("publicKey", publicKeyStr);
        keyPairMap.put("privateKey", privateKeyStr);
        return keyPairMap;
    }

    /**
     * RSA算法公钥加密数据
     *
     * @param data 待加密的明文字符串
     * @param key  RSA公钥字符串
     * @return RSA公钥加密后的经过Base64编码的密文字符串
     */
    public static String buildRSAEncryptByPublicKey(String data, String key) {
        try {
            //通过X509编码的Key指令获得公钥对象
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(key));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            Key publicKey = keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.getEncoder().encodeToString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET)));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }
    /**
     * RSA算法公钥解密数据
     *
     * @param data 待解密的经过Base64编码的密文字符串
     * @param key  RSA公钥字符串
     * @return RSA公钥解密后的明文字符串
     */
    public static String buildRSADecryptByPublicKey(String data, String key) {
        try {
            //通过X509编码的Key指令获得公钥对象
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(key));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            Key publicKey = keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.getDecoder().decode(data)), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }
    /**
     * RSA算法私钥加密数据
     *
     * @param data 待加密的明文字符串
     * @param key  RSA私钥字符串
     * @return RSA私钥加密后的经过Base64编码的密文字符串
     */
    public static String buildRSAEncryptByPrivateKey(String data, String key) {
        try {
            //通过PKCS#8编码的Key指令获得私钥对象
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.getEncoder().encodeToString(rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(CHARSET)));
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + data + "]时遇到异常", e);
        }
    }
    /**
     * RSA算法私钥解密数据
     * @param data 待解密的经过Base64编码的密文字符串
     * @param key  RSA私钥字符串
     * @return RSA私钥解密后的明文字符串
     */
    public static String buildRSADecryptByPrivateKey(String data, String key) {
        try {
            //通过PKCS#8编码的Key指令获得私钥对象
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.getDecoder().decode(data)), CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + data + "]时遇到异常", e);
        }
    }
    /**
     * RSA算法使用私钥对数据生成数字签名
     *
     * @param data 待签名的明文字符串
     * @param key  RSA私钥字符串
     * @return RSA私钥签名后的经过Base64编码的字符串
     */
    public static String buildRSASignByPrivateKey(String data, String key) {
        try {
            //通过PKCS#8编码的Key指令获得私钥对象
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
            Signature signature = Signature.getInstance(ALGORITHM_RSA_SIGN);
            signature.initSign(privateKey);
            signature.update(data.getBytes(CHARSET));
            return Base64.getEncoder().encodeToString(signature.sign());
        } catch (Exception e) {
            throw new RuntimeException("签名字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * RSA算法使用公钥校验数字签名
     *
     * @param data 参与签名的明文字符串
     * @param key  RSA公钥字符串
     * @param sign RSA签名得到的经过Base64编码的字符串
     * @return true--验签通过,false--验签未通过
     */
    public static boolean buildRSAverifyByPublicKey(String data, String key, String sign) {
        try {
            //通过X509编码的Key指令获得公钥对象
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(key));
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
            Signature signature = Signature.getInstance(ALGORITHM_RSA_SIGN);
            signature.initVerify(publicKey);
            signature.update(data.getBytes(CHARSET));
            return signature.verify(Base64.getDecoder().decode(sign));
        } catch (Exception e) {
            throw new RuntimeException("验签字符串[" + data + "]时遇到异常", e);
        }
    }

    /**
     * RSA算法分段加解密数据
     *
     * @param cipher 初始化了加解密工作模式后的javax.crypto.Cipher对象
     * @param opmode 加解密模式,值为javax.crypto.Cipher.ENCRYPT_MODE/DECRYPT_MODE
     * @return 加密或解密后得到的数据的字节数组
     */
    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas) {
        int maxBlock = 0;
        if (opmode == Cipher.DECRYPT_MODE) {
            maxBlock = ALGORITHM_RSA_PRIVATE_KEY_LENGTH / 8;
        } else {
            maxBlock = ALGORITHM_RSA_PRIVATE_KEY_LENGTH / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try {
            while (datas.length > offSet) {
                if (datas.length - offSet > maxBlock) {
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offSet, datas.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        } catch (Exception e) {
            throw new RuntimeException("加解密阀值为[" + maxBlock + "]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        IOUtils.closeQuietly(out);
        return resultDatas;
    }

    public static void main(String[] args){

        String sign = "VqggTZrLKK5Rn6HHGvvi46BP3rpkQm92NBqLwwluslvpuAoH7xrSB0IlYGk/D1+eMtaDuZQPc2fEoDKAWazte6yRNp04KUKzpVCz3B3lcQ0gFBIGzhveMbpytec7XcrBvxLHQRNpGoO/Ju/2OALYAEE0rHkTqZTBBvxvunmA2M23+TJKUWC46+iXmPIPItibAAvKqjRtivyM6xq+auRklOPmtiR4C1S7K8gQ7SS/u/TdoDuKVlea+ATVA/L0ZJvTUNu5eXHfOeq/c3bjIUUj0XVvr22siGjQD+RXKIO4ManvMTz9miftL53ZHVEVvOgm9f4qvPH2r1HK13aFLTlZPQ==";
        String md5 = "0E9B94C2026EB157CE1A03412F97B9E7";
        String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCbBIqHvfORpgP/gUUY844AILMysrptKhUrVeT5c+hMXveTAmshDmvnTGYEueTxtpfgslCVFV9o3iDdmZV/eUL6XWD6FCqwYhoxsCtxfqTpzc1WDDjcgK98v8FOnNHDvzIkrzfIcyqmVyfbiX6GWXk5QzJL/QwMEwPIVCU0OjelK5a65+ZbjXNtG3hokDYlcTwZV0zfyjyfS4Xpy7ucrA3cGylF9SU0p9pwFmXDQrQIfEfv3SCe6PtBwApLhCK2CHoeSmaqsDM1A9KZZ+llpNEsJOE/kGQJEBxxnYMHzsUZY28L3LFKGcMjHGhgetNtH9sSL6017hITKv9O30vRzK/JAgMBAAECggEAW/DnkPbyfZIvkN1E9DWQJTc/0XP76j8paxh4xBMg1hsFT6YBkAuTqA3B/E+dIFeh4N0huiqA+hu09nnfF0n+lavIlspbP14QFyRbv+bTPPCZNs3s86tHaf/5UPzl1OmTffqGZVcZFqtesnDsIBb1NSdLr5IYERk+BxURM9bLpiQ1dSGs+GNKfFk9E4S61zypS3hVFPpic0i5+TbIV9fW8dHhAKqB9l/8Vb5PJREFeWjnPCjnfXL/f4kh6OJEktTfrlKxUkN6jyp1tHVC31lOK7vRTSyS987yFgIoNjYDqosGEbO+cDeK/mFD/PRPDDPvpuhfUi/9xRytI2cDFCi/uQKBgQDTvBZFFdw0N0yQR3vGbP/txpvnPS5ydXdQeQR/VIul5Y8l8XivtgeHiHkPonbj9ntA7tZQU6ISXJHW48HivJU/jD1PGDzICmBqgrGut1FTS/Z6fUtKwHj/QHca9cwsiqScOxdVPAFyeIF2w1tLKvsda7NYSx5sYj17FAKXVa+/HwKBgQC7bP8B2YjQ4gkQvz0YqoyfFJ1jJnOlFgljs45O+2XYI5cSeW/qO7ZXyxsccDkPB7ou+XBFmkD2f56MpXFPk30J5ETdeg0DYRXJmIehdXy/+s1bCtX9tjjrw5HS2q+PSz84oZOUzP+fYiuLecsqNMRwwlgy+BEZiFdx65ClZSD8FwKBgAnBxRTx6O/PDIwpfx6przF50X1aaGWF3DWtHfPwJW9DoNESy4jtTnOEam+6ANiIZy2a0+1nrTRDZ8ULQcf9ALOo9rpKV6KwN7kwxJdUgz7fZUBHvcx4ChtZESQA56O1z9gTMQQQe9CWlQCwB+jy1heI+v66l3BPJp4hUe0/WZXpAoGBALNSKn2rktI/oWrLffoOmzJM9/R4QqcNeXhRv/NjZmKwUodTdrdF4EUJR2wjFwSBYSo2b6u4VTVCjwCD/fCj7Ts8koLQlojIJ59U91eMYAoSPM5BhNIUuUshN3Dk2Jmh5eAZ/2ib5mqo836jzbUY/WXhG5/QZhPmtbuCJUeJhVCLAoGAYs5y/AmPY9/sBnB6XP6AqyEu62WsnnY96mj5ZDIoJR8IFUviM+NhlHH9LF5/Zjzria5F2x9YKJd27bTxrMWjLyc0t9LBAhbi0OeJ+wFiVRf26yTnutCqebJvzbNWpiJY2fU1dXDW0hH19yZpQA2x4OJ6i8BSvhr+4Jw0RCpCJW4=";
        String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzsO4L4mqVmTspNYWR8nYbNfdlJYYFfsLI/IGgmAq2s/5jaaR3kaSJ5EAMEIgA7YQAIJVilX8GkTjfjO4Rc8UYUTrMk6soCYb7+pxMXTiYJxZCVsWqTO0wT9CHGFNOXIqhq8NDmbX+TEXbIj/lHbBYyoMIjtpGsq+djZAzoBffJwx4cEYdMnckU6eN3ff/E1n9BqMqpXqmtXr615dXd4g25KbADPluZ7tyy1LKpovItt1MufIg+YM9oEBXDQqA66836oVtOXxOfiglZdO7mQDz7L4nqNgU/mUXzHD7DIEKlnP3I4tjd23jr7qO28za9o9waytOuL0VEmuBAm++uOHVQIDAQAB";

        boolean result = buildRSAverifyByPublicKey(md5, publicKey,sign);


        System.out.println("RSA result:" + result);
    }

    public String sign(String dataStr, String key) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(key);

        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        Signature signature = Signature.getInstance("MD5withRSA"); // 注意：MD5已经不安全，可以改SHA256withRSA
        signature.initSign(priKey);
        signature.update(dataStr.getBytes("UTF-8"));

        byte[] signBytes = signature.sign();
        return Base64.getEncoder().encodeToString(signBytes);
    }
}
