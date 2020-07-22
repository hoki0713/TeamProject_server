package com.mobeom.local_currency.dummy;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.lang.Integer.parseInt;

public class RandomUserGenerator {
    // 아이디
    public static String generateRandomId() {
        String randomId = "";
        String ran = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        for(int i = 0; i < 6; i++) {
            randomId += ran.charAt((int)(Math.random() * ran.length()));
        }
        return randomId;
    }
    public static String generateRandomNo2() {
        return (int)(Math.random() * 99)+1 +"";
    }

    // 비밀번호
    public static String generateRandomPw() {
        String randomPw = "";
        String ran = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*";
        for(int i = 0; i < 12; i++) {
            randomPw += ran.charAt((int)(Math.random() * ran.length()));
        }
        return randomPw;
    }
    public static String generateRandomPwNum() {
        return (int)(Math.random() * 99)+1 +"";
    }

    // 랜덤 이름
    public static String generateRandomName() {
        List<String> lastName = Arrays.asList("김", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "한", "오", "서", "신",
                "권", "황", "안", "송", "류", "전", "홍", "고", "문", "양", "손", "배", "조", "백", "허", "유", "남", "심",
                "노", "정", "하", "곽", "성", "차", "주", "우", "구", "신", "임", "나", "전", "민", "유", "진", "지", "엄",
                "채", "원", "천", "방", "공", "강", "현", "함", "변", "염", "양", "변", "여", "추", "노", "도", "소", "신",
                "석", "선", "설", "마", "길", "주", "연", "방", "위", "표", "명", "기", "반", "왕", "금", "옥", "육", "인",
                "맹", "제", "모", "장", "남", "탁", "국", "여", "진", "어", "은", "편", "구", "용");
        List<String> firstName = Arrays.asList("가", "강", "건", "경", "고", "관", "광", "구", "규", "근", "기", "길", "나",
                "남", "노", "누", "다", "단", "달", "담", "대", "덕", "도", "동", "두", "라", "래", "로", "루", "리", "마",
                "만", "명", "무", "문", "미", "민", "바", "박", "백", "범", "별", "병", "보", "빛", "사", "산", "상", "새",
                "서", "석", "선", "설", "섭", "성", "세", "소", "솔", "수", "숙", "순", "숭", "슬", "승", "시", "신", "아",
                "안", "애", "엄", "여", "연", "영", "예", "오", "옥", "완", "요", "용", "우", "원", "월", "위", "유", "윤",
                "율", "으", "은", "의", "이", "익", "인", "일", "잎", "자", "잔", "장", "재", "전", "정", "제", "조", "종",
                "주", "준", "중", "지", "진", "찬", "창", "채", "천", "철", "초", "춘", "충", "치", "탐", "태", "택", "판",
                "하", "한", "해", "혁", "현", "형", "혜", "호", "홍", "화", "환", "회", "효", "훈", "휘", "희", "운", "모",
                "배", "부", "림", "봉", "혼", "황", "량", "린", "을", "비", "솜", "공", "면", "탁", "온", "디", "항", "후",
                "려", "균", "묵", "송", "욱", "휴", "언", "령", "섬", "들", "견", "추", "걸", "삼", "열", "웅", "분", "변",
                "양", "출", "타", "흥", "겸", "곤", "번", "식", "란", "더", "손", "술", "훔", "반", "빈", "실", "직", "흠",
                "흔", "악", "람", "뜸", "권", "복", "심", "헌", "엽", "학", "개", "롱", "평", "늘", "늬", "랑", "얀", "향",
                "울", "련");
        Collections.shuffle(lastName);
        Collections.shuffle(firstName);
        return lastName.get(0) + firstName.get(0) + firstName.get(1);
    }

    //생년월일
    public static List<String> yearEnd =
            Arrays.asList("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18");
    public static List<String> yearstart =
            Arrays.asList("19","20");
    public static List<String> month =
            Arrays.asList("01","02","03","04","05","06","07","08","09","10","11","12");
    public static List<String> date =
            Arrays.asList("01","02","03","04","05","06","07","08","09","10","11","12",
                    "13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31");

    public static LocalDate generateRandomBirthDate() {
        Collections.shuffle(yearstart);
        Collections.shuffle(month);
        Collections.shuffle(date);
        String yearDigit = yearstart.get(0);
        String dayDigit = date.get(0);
        String monthDigit = month.get(0);
        if(yearDigit.equals("19")) {
            String yearEnd = ((int)(Math.random() * 69) + 30) + "";
            yearDigit += yearEnd;
        } else if(yearDigit.equals("20")) {
            Collections.shuffle(yearEnd);
            yearDigit += yearEnd.get(0);
        }
        if(parseInt(monthDigit) == 4 || parseInt(monthDigit) == 6 ||
                parseInt(monthDigit) == 9 || parseInt(monthDigit) == 11 && parseInt(dayDigit) > 30) {
            dayDigit = "30";
        }
        if(parseInt(monthDigit) == 2 && parseInt(dayDigit) > 29) {
            dayDigit = "29";
        }
        if(parseInt(yearDigit) % 4 != 0 || parseInt(yearDigit) % 400 != 0 && parseInt(yearDigit) % 100 == 0) {
            if(parseInt(monthDigit) == 2 && parseInt(dayDigit) == 29) {
                dayDigit = "28";
            }
        }
        LocalDate randomBirthDate = LocalDate.of(parseInt(yearDigit),parseInt(monthDigit),parseInt(dayDigit));
        return randomBirthDate;
    }

    //성별
    public static String generateRandomGender() {
        String[] genderList = { "F", "M" };
        int index = ((int)(Math.random()*10)+1);
        return genderList[(index%2)];
    }

    // 이메일
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

    // 가입일
    public static List<String> validMonth =
            Arrays.asList("01","02","03","04","05","06","07","08");

    public static LocalDate generateRandomJoinDate() {

        Collections.shuffle(validMonth);
        Collections.shuffle(date);
        String monthDigit = validMonth.get(0);
        String dayDigit = date.get(0);
        if(parseInt(monthDigit) == 4 || parseInt(monthDigit) == 6 && parseInt(dayDigit) > 30) {
            dayDigit = "30";
        }
        if(parseInt(monthDigit) == 2 && parseInt(dayDigit) > 29) {
            dayDigit = "29";
        }
        LocalDate randomJoinDate = LocalDate.of(2020, parseInt(monthDigit), parseInt(dayDigit));
        return randomJoinDate;
    }

    // 주소
    public static List<String> gyeonggiLocal =
            Arrays.asList("연천군 ","포천시 ","동두천시 ","파주시 ","양주시 ","가평군 ","의정부시 ","고양시 ","김포시 ",
                    "남양주시 ","구리시 ","하남시 ","양평군 ","광주시 ","여주시 ","이천시 ","용인시 ","안성시 ","평택시 ",
                    "화성시 ","수원시 ","오산시 ","안산시 ","군포시 ","의왕시 ","안양시 ","과천시 ","부천시 ","광명시 ",
                    "성남시 ","시흥시 ");

    public static String generateRandomAddress() {
        Collections.shuffle(gyeonggiLocal);
        String randomAddress = "경기도 " + gyeonggiLocal.get(0);
        return randomAddress;
    }
}
