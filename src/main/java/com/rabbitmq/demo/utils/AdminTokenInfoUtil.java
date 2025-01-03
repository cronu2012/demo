package com.rabbitmq.demo.utils;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;

import com.rabbitmq.demo.constant.Constants;
import com.rabbitmq.demo.constant.MerchantConstants;
import com.rabbitmq.demo.utils.dto.MerchantAdminTokenInfoDTO;
import com.rabbitmq.demo.utils.enumm.CurrencyEnum;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.math.BigInteger;
import java.util.List;

public class AdminTokenInfoUtil {
    public static MerchantAdminTokenInfoDTO getMerchantTokenInfoDTO() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String merchantAdminTokenInfoJson = request.getHeader(Constants.MERCHANT_ADMIN_TOKEN_INFO_HEADER);
        if (StringUtils.isBlank(merchantAdminTokenInfoJson)) {
            System.out.println("getMerchantTokenInfoDTO 錯誤");
        }
        return JSONUtil.toBean(merchantAdminTokenInfoJson, MerchantAdminTokenInfoDTO.class);
    }
    public static Long  getMerchantId(){
        return getMerchantTokenInfoDTO().getMerchantId();
    }

    public static Long getMerchantAdminId(){
        return getMerchantTokenInfoDTO().getMerchantAdminId();
    }

    public static Long getRoleId(){
        return getMerchantTokenInfoDTO().getRoleId();
    }

    public static String getMerchantCode(){
        return getMerchantTokenInfoDTO().getMerchantCode();
    }

    public static String getAdminName(){
        return getMerchantTokenInfoDTO().getAdminName();
    }

    public static List<CurrencyEnum> getCurrencyEnumList(){
        return getMerchantTokenInfoDTO().getCurrencyEnumList();
    }

    public static boolean isAdmin() {
        return MerchantConstants.ADMIN_ROLE_ID.equals(getRoleId());
    }

    public static boolean superAdminMerchant(){
        return superAdminMerchant(getMerchantId());
    }
    public static boolean superAdminMerchant(Long merchantId){
        return MerchantConstants.SUPER_ADMIN_MERCHANT_ID.equals(merchantId);
    }

    public static boolean supportCurrencyEnum(CurrencyEnum currencyEnum) {
        Assert.notNull(currencyEnum);
        if (getCurrencyEnumList().contains(currencyEnum)) {
            return true;
        }
        return getCurrencyEnumList().stream().anyMatch(o -> o.equals(currencyEnum));
    }

    public static boolean supportCurrencyEnum(String currencyEnumName) {
        Assert.notNull(currencyEnumName);
        return supportCurrencyEnum(CurrencyEnum.valueOf(currencyEnumName));
    }

    public static void validateMerchantId(Long merchantId) {
        if (!isAdmin() && !getMerchantId().equals(merchantId)) {
            throw new IllegalArgumentException("Illegal merchant id");
        }
    }

    public static Long getRequestMerchantIdNotNull(){
        Long requestMerchantId = getRequestMerchantId();
        Assert.notNull(requestMerchantId, "Header Business-Merchant-Id is null");
        return requestMerchantId;
    }

    private static Long getRequestMerchantId(){
        String merchantIdHeader = RequestUtil.getRequestHeaderByHeaderName(Constants.BUSINESS_MERCHANT_ID_HEADER);
        if(StrUtil.isBlank(merchantIdHeader)){
            return null;
        }
        return Long.parseLong(merchantIdHeader);
    }

    public static CurrencyEnum getRequestCurrencyEnumNotNull(){
        CurrencyEnum requestCurrencyEnum = getRequestCurrencyEnum();
        Assert.notNull(requestCurrencyEnum, "Business-Currency-Enum is null");
        return requestCurrencyEnum;
    }

    private static CurrencyEnum getRequestCurrencyEnum(){
        String currencyEnumHeader = RequestUtil.getRequestHeaderByHeaderName(Constants.BUSINESS_CURRENCY_ENUM_HEADER);
        if(StrUtil.isBlank(currencyEnumHeader)){
            return null;
        }
        return CurrencyEnum.valueOf(currencyEnumHeader);
    }

    public static BigInteger getBigIntegerIpFromRequest(){
        return RequestUtil.getBigIntegerIpFromRequest();
    }

    public static boolean checkLogin() {
        try {
            getMerchantTokenInfoDTO();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
