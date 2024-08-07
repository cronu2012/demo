package com.rabbitmq.demo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HttpUtils {
    private static final OkHttpClient okHttpClient
            = new OkHttpClient().newBuilder()
            .readTimeout(15, TimeUnit.SECONDS).build();

    /**
     * * get請求
     * @param url URL
     * @return String
     */
    public static String doGet(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            log.info("HttpUtils请求出现异常", e);
        }
        return null;
    }

    /**
     * * get請求带参数
     * @param url
     * @return
     */
    public static String doGet(String url, Map<String, String> paramMap) {
        Request request = new Request.Builder()
                .url(url + "?" + HttpUtils.formatUrlParam(paramMap))
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            log.info("HttpUtils请求出现异常", e);
        }
        return null;
    }

    /**
     * * post 表單提交
     * @param url
     * @param body
     * @return
     */
    public static String doPost(String url, RequestBody body) {
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            log.info("HttpUtils请求出现异常", e);
        }
        return null;
    }

    /**
     * post表單提交
     * @param url
     * @param paramMap
     * @return
     */
    public static String doPost(String url, Map<String, String> paramMap) {
        if (paramMap == null)
            throw new InvalidParameterException("Map不可为空");
        FormBody.Builder builder = new FormBody.Builder();
        for(Map.Entry<String, String> e : paramMap.entrySet()) {
            if(e.getValue() != null)
                builder.add(e.getKey(), e.getValue());
        }
        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            log.info("HttpUtils请求出现异常", e);
        }
        return null;
    }

    /**
     * JSON形式提交
     * @param url
     * @param jsonStr
     * @return
     */
    public static String doJsonPost(String url, String jsonStr) {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.Companion.create(jsonStr, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            log.info("HttpUtils请求出现异常", e);
        }
        return null;
    }

    /**
     * Json格式提交 帶請求頭
     * @param url
     * @param jsonStr
     * @param headerMap
     * @return
     */
    public static String doJsonPost(String url, String jsonStr, Map<String, String> headerMap) {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.Companion.create(jsonStr, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .headers(Headers.of(headerMap))
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();
        } catch (Exception e) {
            log.info("HttpUtils请求出现异常", e);
        }
        return null;
    }

//    public static String doPostXmlForm(String path, Map<String, String> params) {
//
//    }

    /**
     * Map转URL键值对
     * @param map
     * @return
     */
    public static String formatUrlParam(Map<String, String> map) {
        if (CollectionUtils.isEmpty(map)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        return sb.toString();
    }

    /**
     *  request parameter to map
     */
    public static Map<String, String> getResMap(HttpServletRequest request) throws IOException {
        Map<String, String[]> params = request.getParameterMap();
        String requestBodyStream = inputStreamToString(request.getInputStream());
        Map<String, String> resMap = (StringUtils.isNotBlank(requestBodyStream))
                ? readValue(requestBodyStream, LinkedHashMap.class) : new LinkedHashMap<>();

        for (Map.Entry<String, String[]> each : params.entrySet()) {
            resMap.put(each.getKey(), each.getValue()[0]);
        }
        return resMap;
    }

    /**
     * request header to map
     */
    public static Map<String, String> getHeaderMap(HttpServletRequest request) {
        Map<String, String> headerMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            headerMap.put(key, value);
        }
        return headerMap;
    }

    public static String inputStreamToString(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        try {
            byte[] b = new byte[4096];
            int n;
            while ((n = in.read(b)) != -1) {
                out.append(new String(b, 0, n));
            }
            return out.toString();
        } catch (IOException ex) {
            throw ex;
        }
    }

    public static Map<String, String> readValue(String reqBody, Class<LinkedHashMap> linkedHashMapClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(reqBody,linkedHashMapClass);
    }

}
