package com.rabbitmq.demo.adapter.in;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class play {
    public static void main(String... args){
        long startTime = System.currentTimeMillis();
//        Map<String, String> paramMap = new TreeMap<>();
//
//        String bankCode = "ธนาคาร ทหารไทยธนชาต(BAY)";
//        if(bankCode != null && bankCode.matches("\\d+")){
//            paramMap.put("bankCode", bankCode);
//        } else {
//            paramMap.put("bankCode", getBankCode(searchBankCode(bankCode)));
//        }
//        long endTime = System.currentTimeMillis();
//        System.out.println("Time taken: " + (endTime - startTime) + " ms");
//        System.out.println(paramMap);

        String bankName = "ธนาคาร เกียรตินาคิน จ ากัด (มหาชน)(KKPaa1)(มหาชน)(AAA)";

        String bankCode = getBankCode2(bankName);

        System.out.println(bankCode);
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
    }

    private static String getBankCode2(String bankName){


        // 正则表达式：匹配括号中的内容
        String regex = "\\(([^)]+)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(bankName);

        // 遍历所有匹配项
        while (matcher.find()) {
            String content = matcher.group(1); // 获取括号中的内容
            if (content.matches(".*[a-zA-Z]+.*")) { // 检查内容是否包含英文字符
               return content;
            }
        }

        return "";
    }

    private static String getBankCode(String bankCode) {
        switch (bankCode) {
            case "KBank":
                return "001";
            case "BBL":
                return "003";
            case "CIMB":
                return "018";
            case "Krungsri":
            case "BAY":
                return "017";
            case "KTB":
                return "004";
            case "LH Bank":
                return "020";
            case "SCB":
                return "010";
            case "SCBT":
                return "013";
            case "TMBThanachart":
                return "007";
            case "UOB":
                return "016";
            case "GSB":
                return "022";
            case "KKP":
                return "023";
            case "Citibank N.A.":
                return "024";
            case "GHBA":
                return "025";
            case "BAAC":
                return "026";
            case "MHCB":
                return "027";
            case "ibank":
                return "028";
            case "Tisco":
                return "029";
            case "ICBC":
                return "030";
            case "Thai":
                return "031";
            case "SMBC":
                return "032";
            case "HSBC":
                return "033";
            case "BNPP":
                return "034";
            case "DEUTSCHE BANK AG":
                return "035";
            case "Bank of China":
                return "036";
            case "INDIAN OVERSEAS BANK":
                return "038";
            case "SME":
                return "039";
            default:
                return "010";
        }
    }

    private static String searchBankCode(String bankName) {

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
