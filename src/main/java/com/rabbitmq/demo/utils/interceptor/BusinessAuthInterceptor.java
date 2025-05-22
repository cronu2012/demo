package com.rabbitmq.demo.utils.interceptor;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.fq.grape.common.api.CommonCode;
import com.fq.grape.common.constans.Constants;
import com.fq.grape.common.constans.CurrencyEnum;
import com.fq.grape.common.constans.EnableEnum;
import com.fq.grape.common.constans.StageConstants;
import com.fq.grape.common.exception.ApiException;
import com.fq.grape.common.util.*;
import com.fq.grape.config.SystemConfig;
import com.fq.grape.module.common.vo.MaitainRespVO;
import com.fq.grape.module.merchant.consts.AccountStatusEnum;
import com.fq.grape.module.merchant.dto.MerchantAdminTokenInfoDTO;
import com.fq.grape.module.merchant.entity.Merchant;
import com.fq.grape.module.merchant.entity.MerchantAdmin;
import com.fq.grape.module.merchant.service.MerchantAdminService;
import com.fq.grape.module.merchant.service.MerchantService;
import com.fq.grape.module.merchant.service.MerchantTokenService;
import com.rabbitmq.demo.utils.AdminTokenInfoUtil;
import com.rabbitmq.demo.utils.RequestUtil;
import io.undertow.servlet.spec.HttpServletRequestImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

/**
 * B端權限校驗攔截器
 */
@Slf4j
public class BusinessAuthInterceptor implements HandlerInterceptor {

    @Autowired
    private MerchantTokenService merchantTokenService;
    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private MerchantAdminService merchantAdminService;
    @Autowired
    private MerchantService merchantService;

    private static final HashSet<String> NOT_VALID_RESOURCE_URI_SET = new HashSet<String>() {{
        // 登录
        this.add("/api/grape/business/merchant/merchantAdmin/login");
        // 登出
        this.add("/api/grape/business/merchant/merchantAdmin/logout");
        // 获取账号信息
        this.add("/api/grape/business/merchant/merchantAdmin/getAccountInfo");
        // 获取资源
        this.add("/api/grape/business/merchant/merchantAdmin/listResource");
        // 获取资源权限
        this.add("/api/grape/business/merchant/merchantAdmin/listPermission");
        // 站点初始化数据
        this.add("/api/grape/business/common/index/getInitData");
        //获取所有活动类型
        this.add("/api/grape/business/activity/getAllActivityTypeEnum");
    }};

    @SuppressWarnings("all")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (systemConfig.isSystemMaintenance()) {
            throw new ApiException(CommonCode.SYSTEM_MAINTENANCE);
        }

        if (AuthUtil.apiNotAuthCheck(handler)) {
            return true;
        }

        MerchantAdminTokenInfoDTO merchantAdminTokenInfoDTO = validateToken(request);

        if (AuthUtil.uploadAuthCheck(handler)) {
            return true;
        }

        validateGoogleVerify(request, handler, merchantAdminTokenInfoDTO);

        HttpServletRequestImpl httpServletRequest = RequestUtil.castHttpServletRequestImpl(request);

        IPSubNetValidateUtil.validateIpByWhitelistIpJson(merchantAdminTokenInfoDTO.getBusinessWhitelistIpJson());

        validateMerchantId(httpServletRequest, merchantAdminTokenInfoDTO);

        validateCurrencyEnum(httpServletRequest, merchantAdminTokenInfoDTO);

        validateResource(httpServletRequest, merchantAdminTokenInfoDTO);

        RequestUtil.addHeader(httpServletRequest, Constants.MERCHANT_ADMIN_TOKEN_INFO_HEADER, merchantAdminTokenInfoDTO);

        return true;
    }

    private MerchantAdminTokenInfoDTO validateToken(HttpServletRequest request) {
        String token = request.getHeader(Constants.BUSINESS_TOKEN_HEADER);
        Assert.notBlank(token, () -> new ApiException(CommonCode.MERCHANT_LOGIN_EXPIRED));
        MerchantAdminTokenInfoDTO merchantAdminTokenInfoDTO = merchantTokenService.getMerchantAdminTokenInfoDTO(token);
        Assert.notNull(merchantAdminTokenInfoDTO, () -> new ApiException(CommonCode.MERCHANT_LOGIN_EXPIRED));
        return merchantAdminTokenInfoDTO;
    }

    private void validateMerchantId(HttpServletRequestImpl httpServletRequest, MerchantAdminTokenInfoDTO merchantAdminTokenInfoDTO) {
        String merchantId = httpServletRequest.getHeader(Constants.BUSINESS_MERCHANT_ID_HEADER);
        if (StrUtil.isBlank(merchantId)) {
            return;
        }
        //校验商户的维护状态
        checkMerchantMaintainStatus(merchantId , merchantAdminTokenInfoDTO);

        if (AdminTokenInfoUtil.superAdminMerchant(merchantAdminTokenInfoDTO.getMerchantId())) {
            return;
        }

        Long merchantIdLong = Long.parseLong(merchantId);

        if (!merchantAdminTokenInfoDTO.getMerchantId().equals(merchantIdLong)
                && !merchantTokenService.isChildrenMerchant(merchantIdLong, merchantAdminTokenInfoDTO.getMerchantId())) {
            log.warn("MerchantId is not match token merchantId ");
            throw new ApiException(CommonCode.MERCHANT_ILLEGAL_PARAM);
        }
    }

    private void validateCurrencyEnum(HttpServletRequestImpl httpServletRequest, MerchantAdminTokenInfoDTO merchantAdminTokenInfoDTO) {
        String currencyEnumName = httpServletRequest.getHeader(Constants.BUSINESS_CURRENCY_ENUM_HEADER);
        if (StrUtil.isBlank(currencyEnumName)) {
            return;
        }

        if (AdminTokenInfoUtil.superAdminMerchant(merchantAdminTokenInfoDTO.getMerchantId())) {
            return;
        }

        // 商户支持所有货币则直接返回不需要判断
        if (merchantAdminTokenInfoDTO.getCurrencyEnumList().contains(CurrencyEnum.ALL)) {
            return;
        }

        if (!merchantAdminTokenInfoDTO.getCurrencyEnumList().contains(CurrencyEnum.valueOf(currencyEnumName))) {
            log.warn("CurrencyEnum is not support merchant, merchantId:{}, currencyEnumName:{}", merchantAdminTokenInfoDTO.getMerchantId(), currencyEnumName);
            throw new ApiException(CommonCode.MERCHANT_ILLEGAL_PARAM);
        }
    }

    private static void validateResource(HttpServletRequestImpl httpServletRequest, MerchantAdminTokenInfoDTO merchantAdminTokenInfoDTO) {
        String requestUri = httpServletRequest.getRequestURI();
        if (NOT_VALID_RESOURCE_URI_SET.contains(requestUri)) {
            return;
        }

        if (!merchantAdminTokenInfoDTO.getBackendUriSet().contains(requestUri)) {
            log.warn("validateResource fail, requestUri:{}, uriSet: {}", requestUri, merchantAdminTokenInfoDTO.getBackendUriSet());
            throw new ApiException(CommonCode.MERCHANT_NO_PERMISSION);
        }
    }

    private void validateGoogleVerify(HttpServletRequest request, Object handler, MerchantAdminTokenInfoDTO merchantAdminTokenInfoDto) {
        GoogleVerify googleVerify;
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            googleVerify = method.getAnnotation(GoogleVerify.class);
            if (googleVerify == null) {
                return;
            }
        }

        String code = request.getHeader(Constants.GOOGLE_CODE);
        if (StringUtils.isBlank(code)) {
            throw new ApiException(CommonCode.PARAM_INVALID);
        }

        MerchantAdmin merchantAdmin = merchantAdminService.getByAdminNameAndMerchantId(merchantAdminTokenInfoDto.getAdminName(), merchantAdminTokenInfoDto.getMerchantId(), AccountStatusEnum.ENABLE);
        if (merchantAdmin.getEnableGaEnum() == EnableEnum.FALSE) {
            throw new ApiException(CommonCode.BIND_GOOGLE_AUTHENTICATOR);
        }

        String secret = merchantAdmin.getGaSecret();

        if (StringUtils.isBlank(secret)) {
            throw new ApiException(CommonCode.GOOGLE_AUTH_ERROR);
        }

        try {
            if (!GoogleAuthUtil.checkGoogleAuth(secret, Integer.parseInt(code))) {
                throw new ApiException(CommonCode.GOOGLE_AUTH_ERROR);
            }
        } catch (NumberFormatException e) {
            throw new ApiException(CommonCode.PARAM_INVALID);
        }
    }

    private void checkMerchantMaintainStatus(String merchantId, MerchantAdminTokenInfoDTO merchantAdminTokenInfoDTO) {
        Merchant merchant = merchantService.getById(merchantId);
        if(Objects.nonNull(merchant)&& (EnableEnum.TRUE.equals(merchant.getMaintainStatusEnum()))){
            log.debug("merchant is maintain merchant:{} , merchantAdminTokenInfoDTO:{}" , merchant , merchantAdminTokenInfoDTO);
            //查询当前登录的账号,忽略admin以及所有上级
            if(!StageConstants.ADMIN_NAME.equals(merchantAdminTokenInfoDTO.getMerchantCode())){
                if(!(Arrays.asList(merchant.getPath().split("/")).contains(merchantAdminTokenInfoDTO.getMerchantCode()))&& !Objects.equals(merchant.getId(), merchantAdminTokenInfoDTO.getMerchantId())){
                    MaitainRespVO maitainRespVO = new MaitainRespVO();
                    maitainRespVO.setId(merchant.getId());
                    maitainRespVO.setMaintainEndTime(merchant.getMaintainEndTime());
                    maitainRespVO.setMaintainRemark(merchant.getMaintainRemark());
                    maitainRespVO.setMaintainStatusEnum(merchant.getMaintainStatusEnum());
                    throw new ApiException(CommonCode.MERCHANT_MAINTENANCE , JSONUtil.toJsonStr(maitainRespVO));
                }
            }
        }
    }

}
