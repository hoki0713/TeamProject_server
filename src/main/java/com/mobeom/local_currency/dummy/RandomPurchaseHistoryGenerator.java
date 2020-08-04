package com.mobeom.local_currency.dummy;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Integer.parseInt;

public class RandomPurchaseHistoryGenerator {
    public static List<String> month =
            Arrays.asList("01","02","03","04","05","06","07","08");

    public static List<String> date =
            Arrays.asList("01","02","03","04","05","06","07","08","09","10","11","12",
                    "13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31");

    public static LocalDate generateRandomDate() {
        Collections.shuffle(month);
        Collections.shuffle(date);
        String monthDigit = month.get(0);
        String dateDigit = date.get(0);

        if(parseInt(monthDigit) == 4 || parseInt(monthDigit) == 6 && parseInt(dateDigit) > 30) {
            dateDigit = "30";
        }
        if(parseInt(monthDigit) == 2 && parseInt(dateDigit) > 29) {
            dateDigit = "29";
        }
        LocalDate randomDate = LocalDate.of(2020, parseInt(monthDigit), parseInt(dateDigit));
        return randomDate;
    }

    public static List<Boolean> booleanList =
            Arrays.asList(true, false, false, false, false);

    public static Boolean generateRandomBoolean() {
        Collections.shuffle(booleanList);
        return booleanList.get(0);
    }

    public static List<String> listOfState =
        Arrays.asList("미사용", "사용완료", "취소완료", "미사용", "사용완료", "사용완료");

    public static String generateRandomCurrencyState() {
        Collections.shuffle(listOfState);
        return listOfState.get(0);
    }

    public static List<Integer> listOfPrice =
            Arrays.asList(5000,10000,15000);

    public static int generateRandomVoucherPrice() {
        Collections.shuffle(listOfPrice);
        return listOfPrice.get(0);
    }

    public static List<String> paymentCompanies =
            Arrays.asList("삼성카드", "신한카드", "롯데카드", "우리카드", "국민카드", "비씨카드", "하나카드");

    public static String generateRandomPaymentCompany() {
        Collections.shuffle(paymentCompanies);
        return paymentCompanies.get(0);
    }

    public static String generateRandomEmailId() {
        String randomEmailId = "";
        String ran = "abcdefghijklmnopqrstuvwxyz1234567890";
        for(int i = 0; i < 6; i++) {
            randomEmailId += ran.charAt((int)(Math.random() * ran.length()));
        }
        return randomEmailId;
    }
    public static String generateRandomEmail() {
        String randomEmail = "";
        String ran = "abcdefghijklmnopqrstuvwxyz";
        for(int i = 0; i < 6; i++) {
            randomEmail += ran.charAt((int)(Math.random() * ran.length()));
        }
        return randomEmail;
    }

    public static String generateRandomEmailEnd() {
        String randomEmailEnd = "";
        String[] ran = {".kr",".com",".ac.kr",".co.kr",".net"};
        int ranIndex = (int)(Math.random() * ran.length);
        randomEmailEnd += ran[ranIndex];
        return randomEmailEnd;
    }

}
