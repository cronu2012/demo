package com.rabbitmq.demo.utils.interceptor;

import cn.hutool.core.lang.Assert;
import cn.hutool.crypto.digest.DigestUtil;
import com.fq.grape.common.api.CommonCode;
import com.fq.grape.common.constans.Constants;
import com.fq.grape.common.exception.ApiException;
import com.fq.grape.common.util.IPSubNetValidateUtil;
import com.fq.grape.config.SystemConfig;
import com.fq.grape.module.merchant.dto.MerchantKeyVerifyInfoDTO;
import com.fq.grape.module.merchant.service.MerchantService;
import com.rabbitmq.demo.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * E端權限校驗攔截器
 */
@Slf4j
public class ExternalAuthInterceptor implements HandlerInterceptor {
    private static final String SIGN_PARAMETER_NAME = "sign";
    private static final String OPERATOR_TOKEN_PARAMETER_NAME = "operatorToken";
    private static final String SECRET_KEY_PARAMETER_NAME = "secretKey";
    private static final List<String> MUST_CONTAINS_PARAMETER_LIST = Arrays.asList(OPERATOR_TOKEN_PARAMETER_NAME, SECRET_KEY_PARAMETER_NAME, SIGN_PARAMETER_NAME, Constants.TRACE_ID_PARAM_NAME);

    private static final List<String> SKIP_SIGN_VERIFY_PARAMETER_LIST = Arrays.asList(Constants.TRACE_ID_PARAM_NAME, SIGN_PARAMETER_NAME);

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private SystemConfig systemConfig;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (systemConfig.isSystemMaintenance()) {
            throw new ApiException(CommonCode.SYSTEM_MAINTENANCE);
        }

        String currentPath = request.getRequestURI() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
        log.debug("######[externalPreHandlePath：{}] ######", currentPath);

        //此路径接口必须包含参数: appId appSecret sign
        Map<String, String[]> parameterMap = request.getParameterMap();
        Set<String> parameterNames = parameterMap.keySet();
        if (!systemConfig.isSkipVerify() && !parameterNames.containsAll(MUST_CONTAINS_PARAMETER_LIST)) {
            throw new ApiException(CommonCode.PARAM_INVALID, "Must contain parameter: sign appId appSecret");
        }

        //验证 sign
        String sign = request.getParameter(SIGN_PARAMETER_NAME);
        boolean verifySignResult = verifySign(parameterMap, sign);
        if (!systemConfig.isSkipVerify() && !verifySignResult) {
            throw new ApiException(CommonCode.PARAM_INVALID, "Sign verification failed");
        }

        ///纯计算逻辑先执行，查库操作后执行
        //验证 appId appSecret 是否匹配且对应商户是否可用
        String operatorToken = request.getParameter(OPERATOR_TOKEN_PARAMETER_NAME);
        String secretKey = request.getParameter(SECRET_KEY_PARAMETER_NAME);
        MerchantKeyVerifyInfoDTO merchantKeyVerifyInfoDTO = verifyAppMerchantData(operatorToken, secretKey);

        IPSubNetValidateUtil.validateIpByWhitelistIpJson(merchantKeyVerifyInfoDTO.getExternalWhitelistIpJson());

        RequestUtil.addHeader(request, Constants.MERCHANT_ID_PARAM, merchantKeyVerifyInfoDTO.getMerchantId());
        return true;
    }

    private boolean verifySign(Map<String, String[]> parameterMap, String sign) {
        //根据参数名排序，然后按照值将其md5进行验证
        TreeMap<String, String> parameterTreeMap = new TreeMap<>();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String parameterName = entry.getKey();
            String parameterValue = Arrays.stream(entry.getValue()).collect(Collectors.joining(""));
            //跳过sign、trade_id参数
            if (!SKIP_SIGN_VERIFY_PARAMETER_LIST.contains(parameterName)) {
                parameterTreeMap.put(parameterName, parameterValue);
            }
        }

        String parameterValueConcat = parameterTreeMap.values().stream().collect(Collectors.joining(""));
        String parameterValuesMd5 = DigestUtil.md5Hex(parameterValueConcat);
        return Objects.equals(sign, parameterValuesMd5);
    }

    /**
     * 验证并且返回 商户ID
     * @return 数据正常返回商户ID(merchantId)，数据异常返回直接抛异常
     */
    private MerchantKeyVerifyInfoDTO verifyAppMerchantData(String operatorToken, String secretKey) {
        Assert.notBlank(operatorToken, "Request operatorToken is blank");
        Assert.notBlank(secretKey, "Request secretKey is blank");
        MerchantKeyVerifyInfoDTO verifyInfo = merchantService.getVerifyInfo(operatorToken);
        Assert.equals( verifyInfo.getSecretKey(), secretKey, ()-> new ApiException(CommonCode.MERCHANT_ILLEGAL_API_KEY));
        return verifyInfo;
    }
}
