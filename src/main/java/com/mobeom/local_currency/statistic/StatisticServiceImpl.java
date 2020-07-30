package com.mobeom.local_currency.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Component
interface StatisticService {

    Map<String, Long> getUserRegionStat(List<String> listOfRegion);
}

@Service
public class StatisticServiceImpl implements StatisticService{
    @Autowired
    StatisticRepository statisticRepository;

    @Override
    public Map<String, Long> getUserRegionStat(List<String> listOfRegion) {
        Map<String, Long> resultList = statisticRepository.getUserRegionStat(listOfRegion);
        return resultList;
    }
}
