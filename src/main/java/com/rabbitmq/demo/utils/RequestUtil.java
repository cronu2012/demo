package com.rabbitmq.demo.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;


import cn.hutool.json.JSONUtil;
import com.rabbitmq.demo.constant.Constants;
import io.undertow.servlet.spec.HttpServletRequestImpl;
import io.undertow.util.HeaderMap;
import io.undertow.util.HttpString;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import java.io.InputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * 请求工具类
 * Created by macro on 2020/10/8.
 */
@Slf4j
public class RequestUtil {

    /**
     * 获取请求真实IP地址
     */
    public static String getRequestIp(HttpServletRequest request) {
        //通过HTTP代理服务器转发时添加
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            // 从本地访问时根据网卡取本机配置的IP
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inetAddress.getHostAddress();
            }
        }
        // 通过多个代理转发的情况，第一个IP为客户端真实IP，多个IP会按照','分割
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    public static String getRequestIpFromRequest(){
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            return getRequestIp(request);
        }catch (Exception e){
            log.warn("getRequestIpFromHttpServletRequest exception", e);
            return StrUtil.EMPTY;
        }
    }

    public static BigInteger getBigIntegerIpFromRequestNotNull(){
        BigInteger bigIntegerIpFromRequest = getBigIntegerIpFromRequest();
        Assert.notNull(bigIntegerIpFromRequest, "Request ip is null");
        return bigIntegerIpFromRequest;
    }

    public static BigInteger getBigIntegerIpFromRequest(){
        return IPUtils.convertIpToBigInteger(getRequestIpFromRequest());
    }

    public static String getRequestHeaderByHeaderName(String headerName){
        Assert.notBlank(headerName);
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            return request.getHeader(headerName);
        }catch (Exception e){
            log.warn("getRequestHeaderByHeaderName exception, headerName:{}", headerName, e);
            return StrUtil.EMPTY;
        }
    }

    public static String getServletRequestHeaderByHeaderName(ServletRequest servletRequest, String headerName) {
        try {
            if (Objects.isNull(servletRequest)) {
                return StrUtil.EMPTY;
            }
            if (StrUtil.isBlank(headerName)) {
                return StrUtil.EMPTY;
            }
            if (servletRequest instanceof HttpServletRequest) {
                HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
                return httpRequest.getHeader(Constants.TRACE_ID_HEADER);
            } else {
                return StrUtil.EMPTY;
            }
        } catch (Exception e) {
            log.warn("getServletRequestHeaderByHeaderName exception, headerName:{}", headerName, e);
            return StrUtil.EMPTY;
        }
    }

    /**
     * 获取请求的完整域名
     * @return <scheme> "://" <hostname> ":" <port>(Endpoint:https://example.com:8888)
     */
    public static String getEndpoint(){
        return getRequestHeaderByHeaderName(Constants.ENDPOINT_HEADER);
    }

    /**
     * 获取语言请求头
     */
    public static String getLang(){
        return getRequestHeaderByHeaderName(Constants.LANG_HEADER);
    }

    /**
     * 获取请求的域名<hostname>,example:test.com
     *
     * @return hostname
     */
    public static String getHostname() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            URL url = URLUtil.url(request.getRequestURL().toString());
            return url.getHost();
        } catch (Exception e) {
            log.warn("getHostname exception", e);
            return StrUtil.EMPTY;
        }
    }

    public static String inputStreamToString(InputStream in) throws Exception {
        StringBuffer out = new StringBuffer();
        try {
            byte[] b = new byte[4096];
            int n;
            while ((n = in.read(b)) != -1) {
                out.append(new String(b, 0, n));
            }
            return out.toString();
        } catch (Exception e) {
            throw e;
        }
    }


    public static void addHeader(HttpServletRequest httpServletRequest, String headerName, Object headerValue){
        HttpServletRequestImpl httpServletRequestImpl = (HttpServletRequestImpl) httpServletRequest;
        addHeader(httpServletRequestImpl, headerName, headerValue);
    }
    public static void addHeader(HttpServletRequestImpl httpServletRequest, String headerName, Object headerValue) {
        String header = httpServletRequest.getHeader(headerName);
        Assert.isNull(header);
        HeaderMap requestHeaders = httpServletRequest.getExchange().getRequestHeaders();
        requestHeaders.add(HttpString.tryFromString(headerName), JSONUtil.toJsonStr(headerValue));
    }

    public static Long getConsumerMerchantId() {
        String merchantId = getRequestHeaderByHeaderName(Constants.MERCHANT_ID_PARAM);
        return Convert.toLong(merchantId);
    }

}
