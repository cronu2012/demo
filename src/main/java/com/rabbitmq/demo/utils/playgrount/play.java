package com.rabbitmq.demo.utils.playgrount;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.NumberUtil;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class play {
    public static void main(String... args){

        String msm1 = "ktbcs.netbank\n" +
                "ได้รับ +500.00 บาท เข้าบัญชี XXX-X-XX117-1 จากบัญชี กสิกรไทย XXX-X-XX596-5\n" +
                "รับเงินสำเร็จ\n" +
                "UID：10290";

        String msm2 = "ktbcs.netbank\n" +
                "ได้รับ +200.00 บาท เข้าบัญชี XXX-X-XX117-1 จากบัญชี ออมสิน XXXXXXX2988\n" +
                "รับเงินสำเร็จ\n" +
                "UID：10290";

        String msm3 = "ktbcs.netbank\n" +
                "ได้รับ +55.00 บาท เข้าพร้อมเพย์ จากบัญชี กสิกรไทย XXX-X-XX740-6\n" +
                "รับเงินสำเร็จ\n" +
                "UID：10134";



        BankMsmHandleDTO result1 = msgParse2(msm1);
        BankMsmHandleDTO result2 = msgParse2(msm2);
        BankMsmHandleDTO result3 = msgParse2(msm3);

        System.out.println("BankMsmHandleDTO: " + result1);
        System.out.println("BankMsmHandleDTO: " + result2);
        System.out.println("BankMsmHandleDTO: " + result3);

    }

    private static BankMsmHandleDTO msgParse2(String msm) {
        try {
            Assert.notEmpty(msm, "msm is empty");
            if (!msm.contains("ได้รับ") && !msm.contains("รับเงินสำเร็จ")) {
                return null;
            }
            String amountStr = msm.split("\\+")[1].split(" ")[0].replace(",", "");
            Long amount = NumberUtil.mul(amountStr, "1000").longValue();

            String bankAccount = "";
            String bankCode = "";
            if (msm.split("XXX-X-XX").length > 2) {
                bankAccount = msm.split("XXX-X-XX")[2].replace("-", "").substring(0, 4);
            } else if (msm.contains("XXXXXXX")) {
                bankAccount = msm.split("XXXXXXX")[1].substring(0, 4);
            } else {
                bankAccount = msm.split("XXX-X-XX")[1].replace("-", "").substring(0, 4);
            }
            try {
                if (msm.split("XXX-X-XX").length > 2) {
                    String[] bankCodeContain = msm.split("XXX-X-XX")[1].split(" ");
                    bankCode = bankCodeContain[bankCodeContain.length - 1];
                } else {
                    String[] bankCodeContain = msm.split("จากบัญชี")[1].split(" ");
                    bankCode = bankCodeContain[1];
                }
            } catch (Exception e) {
                log.warn("KTBBankAppNotifyThMsgParseServiceImpl msgParse get bankCode fail,msm:{}", msm, e);
            }
            BankMsmHandleDTO bankMsmHandleDTO = BankMsmHandleDTO.builder()
                    .bankCode(bankCode)
                    .bankAccount(bankAccount)
                    .payTime(DateUtil.date())
                    .amount(amount)
                    .build();
            log.debug("KTBBankAppNotifyThMsgParseServiceImpl.msgParse bankMsmHandleDTO:{}", bankMsmHandleDTO);
            return bankMsmHandleDTO;
        } catch (Exception e) {
            log.error("msm 解析异常,msm:{}", msm, e);
        }
        return null;
    }

    private static BankMsmHandleDTO msgParse(String msm) {
        try {
            //ktbcs.netbank
            //ได้รับ +5,000.00 บาท เข้าบัญชี XXX-X-XX310-8 จากบัญชี กรุงเทพ XXX-X-XX640-4
            //รับเงินสำเร็จ
            //2023-07-04 20:05:20
            Assert.notEmpty(msm, "msm is empty");
            if (!msm.contains("ได้รับ") && !msm.contains("รับเงินสำเร็จ")) {
                return null;
            }
            String amountStr = msm.split("\\+")[1].split(" ")[0].replace(",", "");
            Long amount = NumberUtil.mul(amountStr, "1000").longValue();
            String bankAccount = msm.split("XXX-X-XX")[2].replace("-", "").substring(0,4);
            String bankCode = "";
            try {
                String[] bankCodeContain = msm.split("XXX-X-XX")[1].split(" ");
                bankCode = bankCodeContain[bankCodeContain.length - 1];
            }catch (Exception e){
                log.warn("KTBBankAppNotifyThMsgParseServiceImpl msgParse get bankCode fail,msm:{}",msm, e);
            }

            String redisKey = String.format("wn:ktb:notify:lock:%s:%s", bankAccount, amount);


            BankMsmHandleDTO bankMsmHandleDTO = BankMsmHandleDTO.builder()
                    .bankCode(bankCode)
                    .bankAccount(bankAccount)
                    .payTime(DateUtil.date())
                    .amount(amount)
                    .build();
            log.debug("KTBBankAppNotifyThMsgParseServiceImpl.msgParse bankMsmHandleDTO:{}", bankMsmHandleDTO);
            return bankMsmHandleDTO;
        } catch (Exception e) {
            log.error("msm 解析异常,msm:{}", msm, e);
        }
        return null;
    }

    @Data
    @Builder
    public static class BankMsmHandleDTO {
        String bankCode;
        String bankAccount;
        Date payTime;
        Long amount;
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
