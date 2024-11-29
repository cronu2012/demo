package com.rabbitmq.demo.constant;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public interface Constants {
    String DEFAULT_GAME_PLATFORM_CODE = "KG";
    String DEFAULT_GAME_PLATFORM_NAME = "KG";
    String DEFAULT_SYSTEM_NAME = "system";
    Long DEFAULT_SYSTEM_MERCHANT_ID = 1L;

    Long DIRECT_HIGH_LELEL = 1L;

    String DEFAULT_HIGH_MEMBER_NAME = "default";
    Long DEFAULT_HIGH_MEMBER_ID = 0L;
    String DEFAULT_UNKNOWN_NAME = "unknown";
    String KEY_GENERATOR = "keyGenerator";
    String MEMBER_TOKEN_INFO_HEADER = "member-token-info";
    String MERCHANT_ADMIN_TOKEN_INFO_HEADER = "merchant-admin-token-info";
    String MERCHANT_ID_PARAM = "merchantId";
    Long HUNDRED_LONG = 100L;
    Integer HUNDRED_INTEGER = 100;
    BigDecimal HUNDRED_BIG_DECIMAL = BigDecimal.valueOf(100);
    Long DEFAULT_RABBIT_CONSUMER_FAIL_SLEEP_SECONDS = 60L;
    Long DEFAULT_RABBIT_CONSUMER_DISABLE_SLEEP_SECONDS = 60L;
    Long DEFAULT_BATCH_SLEEP_SECONDS = 5L;
    String DISCARD_THREAD_POOL="discardThreadPool";
    String CALL_RUN_THREAD_POOL="callRunThreadPool";
    Long DEFAULT_REDIS_LOCK = 60L;
    String TRACE_ID_PARAM_NAME = "traceId";
    String TRACE_ID_HEADER = "Trace-Id";
    String MDC_TRACE_ID_KEY = "trace_id";
    String BUSINESS_TOKEN_HEADER = "Business-Token";
    String BUSINESS_MERCHANT_ID_HEADER = "Business-Merchant-Id";
    String BUSINESS_CURRENCY_ENUM_HEADER = "Business-Currency-Enum";
    String GOOGLE_CODE = "Google-Code";
    String SMS_CODE = "Sms-Code";

    String UNDER_LINE = "_";
    String ENV_LOCAL = "local";
    String ENV_NOT_LOCAL = "!local";
    String ENV_PROD = "prod";
    String ENV_NOT_PROD = "!prod";

    String LOCAL_CACHE_TINY_NAME = "tiny";

    String LOCAL_CACHE_SHORT_NAME = "short";
    String LOCAL_CACHE_LONG_NAME = "long";
    String LOCAL_CACHE_MEMBER_CACHE_NAME = "member-cache";
    String LOCAL_CACHE_MERCHANT_TOKEN_CACHE_NAME = "merchant-admin-token-cache";
    String LOCAL_CACHE_NOW_GAME_ISSUE_CACHE_NAME = "now-game-issue-cache";
    String LOCAL_CACHE_GAME_NAME = "game-cache";
    String LOCAL_CACHE_MERCHANT_KEY = "merchant-key-cache";
    String LOCAL_CACHE_MERCHANT_CONFIG = "merchant-config";
    String LOCAL_CACHE_MERCHANT_CONFIG_AUDIT_RESET_ZERO = "merchant-config-audit-reset-zero";
    String LOCAL_CACHE_GAME_RESULT_LIST_NAME = "game-result-list";
    String LOCAL_CACHE_GAME_STATISTIC_LIST_NAME = "game-statistic-list";
    String LOCAL_CACHE_ACTIVITY_SAFE_REVENUE = "activity-safe-revenue";
    String LOCAL_CACHE_THIRD_PLATFORM_ALL_MAP = "third-platform-all-map";
    String LOCAL_CACHE_MERCHANT_IP_REGULAR = "merchant-ip-regular";
    String LOCAL_CACHE_MERCHANT_IP_RULE_SWITCH = "merchant-ip-rule-switch";
    String LOCAL_CACHE_THIRD_PLATFORM_PLAYER_EXIST = "third-platform-player-exist";
    String LOCAL_CACHE_MERCHANT_MAINTAIN_INFO = "merchant-Maintain-info";
    String MEMBER_TOKEN_HEADER = "Member-Token";
    String MEMBER_MERCHANT_ID_HEADER = "merchantId";
    Long SEVEN_LONG = 7L;
    String SQL_LIMIT_1 = "limit 1";
    String SQL_LIMIT_1000 = "limit 1000";
    String SQL_LIMIT_5000 = "limit 5000";

    Long CURRENCY_REPORT_QUERY_LIMIT = 3L;
    Integer ONE_DAY_MINUTES = 1440;
    
    String SQL_LIMIT = "LIMIT ";
    Long TEN_THOUSAND = 10000L;

    Integer ZERO_INTEGER = 0;
    Long ZERO_LONG = 0L;

    Integer ONE_INTEGER = 1;
    Long ONE_LONG = 1L;

    Integer THREE_INTEGER = 3;
    Long THREE_LONG = 3L;


    String ENDPOINT_HEADER = "Endpoint";
    String LANG_HEADER = "lang";

    Integer DEFAULT_VIP_LEVEL = 0;

    String THIRD_PLATFORM_THREAD_POOL = "thirdPlatformThreadPool";

    /**
     * 查询三方用户余额专用线程池
     */
    String THIRD_GET_BALANCE_THREAD_POOL = "thirdGetBalanceThreadPool";

    Integer DEFAULT_STR_MAX_LENGTH = 255;

    String UFT8 = "UTF-8";
    String SUFFIX_DOT_00 = ".00";
    String SUFFIX_DOT_10 = ".10";
    String SUFFIX_DOT_20 = ".20";
    String SUFFIX_DOT_30 = ".30";
    String SUFFIX_DOT_40 = ".40";
    String SUFFIX_DOT_50 = ".50";
    String SUFFIX_DOT_60 = ".60";
    String SUFFIX_DOT_70 = ".70";
    String SUFFIX_DOT_80 = ".80";
    String SUFFIX_DOT_90 = ".90";
    Integer SCALE0 = 0;
    Integer SCALE1 = 1;
    Integer SCALE2 = 2;
    Integer SCALE4 = 4;
    Integer TEN_THOUSAND_INT = 1000;

    String EMPTY_JSON = "{}";


    String SHARDING_DATASOURCE_CACHE = "dataSourceCache";
    String SHARDING_CONTEXT_MANAGER = "contextManager";
    String SHARDING_LOGIC_DB = "logic_db";
    String SHARDING_SINGLE_TABLE_DATA_NODES = "singleTableDataNodes";

    List<String> SHARDING_CONFIG_KEY = new ArrayList<String>(){{
        this.add("spring.sharding-sphere.props.sql-show");
        this.add("spring.sharding-sphere.props.check-table-metadata-enabled");
        this.add("spring.sharding-sphere.props.max-connections-size-per-query");

        this.add("spring.sharding-sphere.datasource.master_0.driver-class-name");
        this.add("spring.sharding-sphere.datasource.master_0.jdbc-url");
        this.add("spring.sharding-sphere.datasource.master_0.username");
        this.add("spring.sharding-sphere.datasource.master_0.password");
        this.add("spring.sharding-sphere.datasource.master_0.minimum-idle");
        this.add("spring.sharding-sphere.datasource.master_0.maximum-pool-size");

        this.add("spring.sharding-sphere.datasource.slave_0.driver-class-name");
        this.add("spring.sharding-sphere.datasource.slave_0.jdbc-url");
        this.add("spring.sharding-sphere.datasource.slave_0.username");
        this.add("spring.sharding-sphere.datasource.slave_0.password");
        this.add("spring.sharding-sphere.datasource.slave_0.minimum-idle");
        this.add("spring.sharding-sphere.datasource.slave_0.maximum-pool-size");

        this.add("spring.sharding-sphere.datasource.master_his_0.driver-class-name");
        this.add("spring.sharding-sphere.datasource.master_his_0.jdbc-url");
        this.add("spring.sharding-sphere.datasource.master_his_0.username");
        this.add("spring.sharding-sphere.datasource.master_his_0.password");
        this.add("spring.sharding-sphere.datasource.master_his_0.minimum-idle");
        this.add("spring.sharding-sphere.datasource.master_his_0.maximum-pool-size");

        this.add("spring.sharding-sphere.datasource.slave_his_0.driver-class-name");
        this.add("spring.sharding-sphere.datasource.slave_his_0.jdbc-url");
        this.add("spring.sharding-sphere.datasource.slave_his_0.username");
        this.add("spring.sharding-sphere.datasource.slave_his_0.password");
        this.add("spring.sharding-sphere.datasource.slave_his_0.minimum-idle");
        this.add("spring.sharding-sphere.datasource.slave_his_0.maximum-pool-size");
    }};

    String AES_KEY_CONFIG_KEY = "aes.key";
    String THIRD_AES_KEY_CONFIG_KEY = "third.aes.key";

    String TYPE_HANDLER_PREFIX = "typeHandler=";

    String ONLINE = "线上";
    String OFFLINE = "线下";
    Long DEFAULT_MERCHANT_SMS_CLEAR_SLEEP_SECONDS = 60L;

    /**
     * 支付和代付相关常数
     */
    String CHANNEL = "channel";
    String CURRENCY = "currency";
    String OK = "OK";
    String SUCCESS = "SUCCESS";

}
