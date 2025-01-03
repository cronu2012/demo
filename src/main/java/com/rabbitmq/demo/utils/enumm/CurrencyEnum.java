package com.rabbitmq.demo.utils.enumm;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public enum CurrencyEnum {
    /**
     * 货币枚举，根据iso4217标准命名
     * defaultExchangeRate為貨幣默認與USD匯率，新增貨幣時需要google查詢XXX to USD匯率
     */
    ALL(0, "所有币种", 1, null),
    KVND(1, "千越南盾", 1, new BigDecimal("0.039")),
    THB(2, "泰铢", 1, new BigDecimal("0.027")),
    PHP(3, "菲律宾披索", 1, new BigDecimal("0.017")),
    BRL(4, "巴西里亚伊（雷亚尔）",1, new BigDecimal("0.19")),
    INR(5, "印度卢比",1, new BigDecimal("0.012"))
    ;

    private final Integer code;
    private final String desc;

    private final Integer baseUnit;

    @Getter
    private final BigDecimal defaultExchangeRate;
    public Integer getCode() {
        return code;
    }

    CurrencyEnum(Integer code, String desc, Integer baseUnit, BigDecimal defaultExchangeRate){
        this.code = code;
        this.desc = desc;
        this.baseUnit = baseUnit;
        this.defaultExchangeRate = defaultExchangeRate;
    }

    public static CurrencyEnum findByCode(Integer code) {
        for (CurrencyEnum value : CurrencyEnum.values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        return null;
    }

    /**
     * 返回所有的code
     */
    public static List<Integer> getAllCode() {
        CurrencyEnum[] values = CurrencyEnum.values();
        Integer[] codes = new Integer[values.length];
        for (int i = 0; i < values.length; i++) {
            codes[i] = values[i].code;
        }
        return Arrays.asList(codes);
    }

    public static List<CurrencyEnum> getCurrencyEnumListFRemoveAll() {
        List<CurrencyEnum> currencyEnumList = Arrays.stream(values()).collect(Collectors.toList());
        currencyEnumList.remove(CurrencyEnum.ALL);
        return currencyEnumList;
    }

    public static Map<CurrencyEnum, BigDecimal> getDefaultExchangeRateMap() {
        return Arrays.stream(values())
                .filter(currencyEnum -> currencyEnum.getDefaultExchangeRate() != null)
                .collect(Collectors.toMap(Function.identity(), CurrencyEnum::getDefaultExchangeRate));
    }

    public static List<CurrencyEnum> getCurrencyEnumAndAllList(CurrencyEnum currencyEnum){
        return Arrays.asList(CurrencyEnum.ALL, currencyEnum);
    }

    public static List<CurrencyEnum> parseNamesToEnum(String currencies) {
        return Arrays.stream(currencies.split(","))
                .map(i -> CurrencyEnum.valueOf(i.trim()))
                .collect(Collectors.toList());
    }

}
