package com.rabbitmq.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DemoPlay {

    private static final String json = "";

    public static void main(String[] args){

    }

    private String searchBankCode(String bankName) {
        // 取出括號裡的銀行編碼
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(bankName);

        if (matcher.find()) {
            return matcher.group(1); // group(1) 是括号内的内容
        }

        //都都找不到銀行編碼就先傳SCB
        return "SCB";
    }
}
