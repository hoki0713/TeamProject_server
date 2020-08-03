package com.mobeom.local_currency.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/statistics")
public class StatisticController {
    @Autowired
    StatisticService statisticService;
    @Autowired
    StatisticSources statisticSources;

    @GetMapping(value ="")
    public ResponseEntity<Map<String, Long>> getUserRegionStat() {
        Map<String, Long> resultList = statisticService.getUserRegionStat(statisticSources.getListOfRegion());
        return ResponseEntity.ok(resultList);
    }
}
