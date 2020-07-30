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
    public ResponseEntity<List<Map<?,?>>> getUserRegionStat() {
        Optional<List<Map<?,?>>> resultList = statisticService.getUserRegionStat(statisticSources.getListOfRegion());
        if(resultList.isPresent()) {
            return ResponseEntity.ok(resultList.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
