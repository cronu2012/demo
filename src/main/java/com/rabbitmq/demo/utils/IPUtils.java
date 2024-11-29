package com.rabbitmq.demo.utils;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.StrUtil;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CountryResponse;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotEmpty;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.util.regex.Pattern;

public class IPUtils {
    private static final String UNKNOWN_AREA = "未知区域";
    private static final String CN_CODE = "zh-CN";
    private final static Logger logger = LoggerFactory.getLogger(IPUtils.class);
    public final static String IPV4_PATTERN = "^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$";
    public final static String IPV6_PATTERN = "^\\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*$";
    private static final Pattern PATTERN = Pattern.compile("^((25[0-5]|2[0-4]\\d|[1]{1}\\d{1}\\d{1}|[1-9]{1}\\d{1}|\\d{1})($|(?!\\.$)\\.)){4}$");
    private static final BigInteger MAX_IPV4 = new BigInteger("4294967295");

    private static DatabaseReader INSTANCE = null;

    private static DatabaseReader CITY_INSTANCE = null;

    public static DatabaseReader getInstance() {
        if (INSTANCE == null) {
            synchronized (IPUtils.class) {
                if (INSTANCE == null) {
                    logger.info("初始化DatabaseReader");
                    ClassPathResource classPathResource = new ClassPathResource("GeoLite2-Country.mmdb");
                    try {
                        InputStream database = classPathResource.getInputStream();
                        INSTANCE = new DatabaseReader.Builder(database).build();
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 获取普通IP
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        if (StringUtils.isNotEmpty(ip))
            ip = ip.split(",")[0];
        if (StringUtils.isNotEmpty(ip))
            ip = ip.split(":")[0];
        return ip;
    }

    /**
     * 获取ip类型，非ipv4或ipv6返回null
     */
    public static BigInteger changeIpv4OrIpv6ToBigInteger(String ip) {
        if (StringUtils.isBlank(ip)) {
            return null;
        }

        if (Pattern.matches(IPV4_PATTERN, ip)) {
            return BigInteger.valueOf(NetUtil.ipv4ToLong(ip));
        } else if (Pattern.matches(IPV6_PATTERN, ip)) {
            return NetUtil.ipv6ToBigInteger(ip);
        } else {
            logger.error("parse ip error");
            return null;
        }
    }

    public static String changeBigIntegerToIpv4OrIpv6(BigInteger ip) {
        if (ip == null) {
            return null;
        }

        if (ip.compareTo(MAX_IPV4) <= 0) {
            return NetUtil.longToIpv4(ip.longValue());
        } else if (ip.compareTo(MAX_IPV4) > 0) {
            return NetUtil.bigIntegerToIPv6(ip);
        } else {
            logger.error("parse ip error");
            return null;
        }
    }

    public static long ipToLong(String strIp) {
        if (!PATTERN.matcher(strIp).matches()) {
            logger.error("无效ip地址");
        }
        String[] ip = strIp.split("\\.");
        return (Long.parseLong(ip[0]) << 24) + (Long.parseLong(ip[1]) << 16) + (Long.parseLong(ip[2]) << 8) + Long.parseLong(ip[3]);
    }

    /**
     * 比较两个ip地址，如果两个都是合法地址，则1代表ip1大于ip2，-1代表ip1小于ip2,0代表相等；
     * 如果有其一不是合法地址，如ip2不是合法地址，则ip1大于ip2，返回1，反之返回-1；两个都是非法地址时，则返回0；
     */
    public static int compareIpV4s(String ip1, String ip2) {
        String[] temp1 = ip1.split("\\.");
        String[] temp2 = ip2.split("\\.");

        for (int i = 0; i < 4; i++) {
            int ipInt1 = NumberUtils.toInt(temp1[i]);
            int ipInt2 = NumberUtils.toInt(temp2[i]);
            if (ipInt1 > ipInt2) {
                return 1;
            } else if (ipInt1 < ipInt2) {
                return -1;
            }
        }
        return 0;
    }


    public static int compareIp(String ip1, String ip2) {
        BigInteger ip11;
        BigInteger ip22;
        if (Pattern.matches(IPV4_PATTERN, ip1)) {
            ip11 = BigInteger.valueOf(NetUtil.ipv4ToLong(ip1));
        } else {
            ip11 = NetUtil.ipv6ToBigInteger(ip1);
        }
        if (Pattern.matches(IPV4_PATTERN, ip2)) {
            ip22 = BigInteger.valueOf(NetUtil.ipv4ToLong(ip2));
        } else {
            ip22 = NetUtil.ipv6ToBigInteger(ip2);
        }
        return ip11.compareTo(ip22);
    }


    public static String getCountry(BigInteger ip){
        if(ip == null){
            return StrUtil.EMPTY;
        }
        String ipStr = changeBigIntegerToIpv4OrIpv6(ip);
        return getCountry(ipStr);
    }

    public static String getCountry(@NotEmpty String ip) {
        if (Pattern.matches(IPV4_PATTERN, ip) && NetUtil.isInnerIP(ip)) {
            return UNKNOWN_AREA;
        }
        try {
            InetAddress ipAddress = InetAddress.getByName(ip);
            CountryResponse country = getInstance().country(ipAddress);
            String countryName = country.getCountry().getNames().get(CN_CODE);

            if (StrUtil.isNotEmpty(countryName)) {
                return countryName;
            }

            // 如果IP应用的国家地区不存在则查询IP注册国家地区
            String registeredName = country.getRegisteredCountry().getNames().get(CN_CODE);
            if (StrUtil.isNotEmpty(registeredName)) {
                return registeredName;
            }

            // 如果IP注册国家地区不存在则查询在哪个大洲
            String continentName = country.getContinent().getNames().get(CN_CODE);
            if (StrUtil.isNotEmpty(continentName)) {
                return continentName;
            }

            // 都未查询到则返回未知地区
            return UNKNOWN_AREA;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return UNKNOWN_AREA;
        }
    }

    public static BigInteger convertIpToBigInteger(String ipAddress) {
        try {
            if (StrUtil.isEmpty(ipAddress)) {
                return null;
            } else {
                InetAddress inetAddress = InetAddress.getByName(ipAddress);
                byte[] bytes = inetAddress.getAddress();
                return new BigInteger(1, bytes);
            }
        } catch (Exception var3) {
            logger.error("convertIpToBigInteger exception", var3);
            return null;
        }
    }

}
