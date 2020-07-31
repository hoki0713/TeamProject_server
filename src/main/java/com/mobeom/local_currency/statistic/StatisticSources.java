package com.mobeom.local_currency.statistic;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class StatisticSources {
    String[] region = {"연천군", "포천시", "파주시", "동두천시", "양주시", "의정부시", "가평군", "고양시",
            "김포시", "남양주시", "구리시", "하남시", "양평군", "광주시", "여주시", "이천시", "용인시", "안성시",
            "평택시", "화성시", "수원시", "오산시", "안산시", "군포시", "의왕시", "안양시", "과천시", "부천시",
            "광명시", "성남시", "시흥시", "광명시", "성남시", "시흥시"};
    public List<String> getListOfRegion() {
        List<String> listOfRegion = new ArrayList<>();
        Collections.addAll(listOfRegion, region);
        return listOfRegion;
    }


}
