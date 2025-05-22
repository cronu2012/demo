package com.rabbitmq.demo.utils.interceptor;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;
import com.fq.grape.common.api.CommonCode;
import com.fq.grape.common.constans.Constants;
import com.fq.grape.common.constans.CurrencyEnum;
import com.fq.grape.common.exception.AccessDenyException;
import com.fq.grape.common.exception.ApiException;
import com.fq.grape.common.util.AuthUtil;
import com.fq.grape.config.SystemConfig;
import com.fq.grape.module.common.util.MerchantIpRegularValidator;
import com.fq.grape.module.common.vo.MaitainRespVO;
import com.fq.grape.module.member.dto.MemberTokenInfoDTO;
import com.fq.grape.module.member.service.MemberTokenService;
import com.fq.grape.module.merchant.dto.MerchantCurrencyDTO;
import com.fq.grape.module.merchant.service.MerchantService;
import com.fq.grape.module.merchant.service.MerchantSmsService;
import com.rabbitmq.demo.utils.RequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * C端權限校驗攔截器
 */
@Slf4j
public class ConsumerAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private MemberTokenService memberTokenService;

    @Autowired
    private MerchantIpRegularValidator merchantIpRegularValidator;

    @Autowired
    private MerchantSmsService merchantSmsService;
    @Autowired
    private MerchantService merchantService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (systemConfig.isSystemMaintenance()) {
            throw new ApiException(CommonCode.SYSTEM_MAINTENANCE);
        }

        String merchantId = RequestUtil.getRequestHeaderByHeaderName(Constants.MERCHANT_ID_PARAM);

        // 拦截IP规则
        checkMerchantIpRule(request, Long.valueOf(merchantId));

        //校验商户的维护状态
        checkMerchantMaintainStatus(Long.parseLong(merchantId));

        if (AuthUtil.apiNotAuthCheck(handler)) {
            return true;
        }

        MemberTokenInfoDTO tokenInfoDTO = parseMemberToken(request);

        if (AuthUtil.uploadAuthCheck(handler)) {
            return true;
        }

        //此部分代码暂时注释掉，后续有其他需求需要此处功能再启用。
//        if (AuthUtil.smsAuthCheck(handler)) {
//            if (!merchantSmsService.checkCode(tokenInfoDTO.getMerchantId(), tokenInfoDTO.getCurrencyEnum(),
//                    tokenInfoDTO.getAreaCode(), String.valueOf(tokenInfoDTO.getMobile()), request.getHeader(Constants.SMS_CODE))) {
//                throw new ApiException(CommonCode.SMS_CODE_ERROR);
//            }
//        }

        RequestUtil.addHeader(request, Constants.MEMBER_TOKEN_INFO_HEADER, tokenInfoDTO);
        return true;
    }

    private void checkMerchantIpRule(HttpServletRequest request, Long merchantId) {
        String ipAddress = RequestUtil.getRequestIpFromRequest();
        CurrencyEnum currencyEnum = getCurrentCurrencyEnum(request, merchantId, ipAddress);
        try {
            merchantIpRegularValidator.validateIpRule(Convert.toLong(merchantId), ipAddress, currencyEnum, false);
        } catch (AccessDenyException e) {
            throw new AccessDenyException(e.getErrorCode(), e.getMessage(), merchantService.merchantIPAccessDenyInfoCache(merchantId));
        }
    }

    private CurrencyEnum getCurrentCurrencyEnum(HttpServletRequest request, Long merchantId, String ip) {
        String token = request.getHeader(Constants.MEMBER_TOKEN_HEADER);
        CurrencyEnum currencyEnum = null;
        if (StringUtils.isNotBlank(token)) {
            MemberTokenInfoDTO memberTokenInfoDTO = memberTokenService.getByToken(token);
            currencyEnum = memberTokenInfoDTO != null ? memberTokenInfoDTO.getCurrencyEnum() : null;
        }
        if (currencyEnum == null) {
            MerchantCurrencyDTO merchantCurrencyDTO = merchantService.getCurrencyListAndIpCurrency(merchantId, ip);
            currencyEnum = merchantCurrencyDTO.getAvailableCurrency();
        }
        return currencyEnum;
    }

    private MemberTokenInfoDTO parseMemberToken(HttpServletRequest request) {
        String token = request.getHeader(Constants.MEMBER_TOKEN_HEADER);
        Assert.notBlank(token, () -> new ApiException(CommonCode.MEMBER_NOT_LOGIN));
        MemberTokenInfoDTO memberTokenInfoDTO = memberTokenService.getByToken(token);
        Assert.notNull(memberTokenInfoDTO, () -> new ApiException(CommonCode.MEMBER_NOT_LOGIN));
        return memberTokenInfoDTO;
    }

    private void checkMerchantMaintainStatus(Long merchantId) {
        if(ObjectUtils.isNotEmpty(merchantId)){
            MaitainRespVO maitainRespVO = merchantService.merchantMaintainInfoCache(merchantId);
            if(ObjectUtils.isNotEmpty(maitainRespVO)){
                throw new AccessDenyException(CommonCode.MERCHANT_MAINTENANCE , JSONUtil.toJsonStr(maitainRespVO));
            }
        }

    }

}
