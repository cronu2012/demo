package com.rabbitmq.demo.constant;

/**
 * Description:
 *
 * <p>
 * Create on 2023/11/3.
 * <p>
 *
 * @author wendell
 * @version 0.1
 */
public interface MerchantConstants {

    Long SUPER_ADMIN_MERCHANT_ID = 1L;

    Long ADMIN_ROLE_ID = 0L;

    Long DEFAULT_MERCHANT_ROLE_ID = 1L;

    Integer GA_OFFSET_SECONDS = 10;

    int DEFAULT_PASSWD_LENGTH = 16;

    String SMS_CODE_PLACEHOLDER = "[sms_code]";

    String ITNIOTECH_SMS_URL = "https://api.itniotech.com/sms/sendSms";
}
