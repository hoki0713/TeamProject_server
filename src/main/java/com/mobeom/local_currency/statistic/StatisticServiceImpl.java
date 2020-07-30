package com.mobeom.local_currency.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
interface StatisticService {

    Optional<List<Map<?,?>>> getUserRegionStat(List<String> listOfRegion);
}

@Service
public class StatisticServiceImpl implements StatisticService{
    @Autowired
    StatisticRepository statisticRepository;

    @Override
    public Optional<List<Map<?, ?>>> getUserRegionStat(List<String> listOfRegion) {
        Optional<List<Map<?,?>>> resultList = statisticRepository.getUserRegionStat(listOfRegion);
        return resultList;
    }
}
