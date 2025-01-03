package com.rabbitmq.demo.utils.dto;


import com.rabbitmq.demo.utils.enumm.CurrencyEnum;
import lombok.Data;

import java.io.Serializable;

import java.util.List;
import java.util.Set;

@Data
public class MerchantAdminTokenInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String adminName;

    private Long merchantAdminId;

    private Long merchantId;
    private String merchantCode;
    private Long roleId;
    private String businessWhitelistIpJson;
    private Set<String> backendUriSet;
    private List<CurrencyEnum> currencyEnumList;

}
