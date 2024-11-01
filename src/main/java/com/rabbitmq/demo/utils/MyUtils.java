package com.rabbitmq.demo.utils;

public class MyUtils {
   private static final String INR_LIST = "{\n" +
           "        \"banklist\": [\n" +
           "            {\n" +
           "                \"bankID\": 12,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"HDFC Bank\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 9,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"IDBI Bank\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 6,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"ICICI Bank\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 17,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"FEDERAL BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 24,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"IDFC Bank\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 34,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"Karur Vysya Bank\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 48,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"RBL Bank\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 77,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"ORIENTAL BANK OF COMMERCE\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 83,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"AIRTEL PAYMENTS BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 85,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"DBS BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 139,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"STATE BANK OF TRAVANCORE\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 115,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"UJJIVAN BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 142,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"ICCCORP\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 105,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"BHARAT BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 102,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"DENA BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 143,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"AIRTEL BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 31,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"INDIAN POST PAYMENT BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 144,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"RATNAKAR BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 91,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"CATHOLIC SYRIAN BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 146,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"DCB BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 49,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"DHANLAXMI BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 16,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"BANK OF BARODA\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 41,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"SOUTH INDIAN BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 47,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"KERALA GRAMIN BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 150,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"BHARATPE\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 148,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"ANDHARA PRADESH GRAMEENA VIKAS BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 147,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"SMALL INDUSTRIES DEVELOPMENT BANK OF INDIA\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 151,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"THE SUMITOMO BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 152,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"STATE BANK OF MAURITIUS\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 153,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"SOCIETE GENERALE\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 154,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"MASHREQ BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 155,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"KRUNG THAI BANK PUBLIC CO\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 156,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"ING BANK NV\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 157,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"HSBC\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 158,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"CHINA TRUST COMMERCIAL BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 159,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"BNP PARIBAS\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 160,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"BARCLAYS BANK PLC\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 161,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"BANK OF TOKYO-MITSUBISH\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 162,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"BANK OF NOVA SCOTIA\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 163,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"BANK OF CEYLON\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 164,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"BANK OF BAHRAIN KUWAIT BSC\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 165,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"ANZ GRINDLAYS BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 166,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"ABU DHABI COMMERCIAL BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 167,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"AMERICAN EXPRESS BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 168,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"ABN-AMRO BANK NV\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 35,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"TAMILNADU MERCANTILE BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 40,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"LAKSHMI VILAS BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 169,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"ING VYSYA BANK LTD\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 33,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"CITY UNION BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 170,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"STATE BANK OF PATIALA\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 56,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"FINO PAYMENTS BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 172,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"STATE BANK OF BIKANER JAIPUR\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 3,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"STATE BANK OF INDIA\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 173,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"IDBI BANK LIMITED\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 119,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"VIJAYA BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 120,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"UNITED BANK OF INDIA\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 32,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"CORPORATION BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 60,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"ANDHRA PRAGATHI GRAMEENA BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 1,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"AXIS BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 7,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"PUNJAB NATIONAL BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 5,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"CANARA BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 4,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"KOTAK MAHINDRA BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 2,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"INDIAN BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 27,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"INDUSIND BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 26,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"BANDHAN BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 23,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"INDIAN OVERSEAS BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 22,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"CITIBANK INDIA\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 21,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"UCO BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 20,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"BANK OF MAHARASHTRA\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 19,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"ANDHRA BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 15,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"UNION BANK OF INDIA\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 14,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"CENTRAL BANK OF INDIA\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 13,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"YES BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": sql-inrlist,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"KARNATAKA BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 10,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"STANDARD CHARTERED BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 8,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"BANK OF INDIA\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 86,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"EQUITAS SMALL FINANCE BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            },\n" +
           "            {\n" +
           "                \"bankID\": 174,\n" +
           "                \"bankLogo\": \"https://ossimg.9987cw.cc/TC\",\n" +
           "                \"bankName\": \"NANITAL BANK\",\n" +
           "                \"reserved\": \"1\"\n" +
           "            }\n" +
           "        ]\n" +
           "    }";


   private static String extractAcronym(String input) {
      // 將字串分割為單字陣列，並過濾掉 "OF"
      String[] words = input.split(" ");
      StringBuilder acronym = new StringBuilder();

      for (String word : words) {
         // 過濾掉 "OF" 並取其他單詞的首字母
         if (!word.equalsIgnoreCase("OF") && !word.isEmpty()) {
            acronym.append(word.charAt(0));
         }
      }

      // 將結果轉為大寫
      return acronym.toString().toUpperCase();
   }

   public static void main(String[] args){

   }
}
