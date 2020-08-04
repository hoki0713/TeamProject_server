package com.mobeom.local_currency.store;


import com.amazonaws.services.dynamodbv2.xspec.L;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Component
interface StoreService{
    Map<String,Long> storeLocalsChart(String localSelect);
    Map<String,Long> storeTypeChart();
}


@Service
public class StoreServiceImpl implements StoreService {

    @Autowired StoreRepository storeRepository;

    @Override
    public Map<String, Long> storeLocalsChart(String localSelect) {
        return storeRepository.localsTotal(localSelect);
    }

    @Override
    public Map<String, Long> storeTypeChart() {
        return storeRepository.storeTypeLocal();
    }


}
