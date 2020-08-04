package com.mobeom.local_currency.recommend;

import com.mobeom.local_currency.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
interface RecommendService {
    void recommendStore(List<String> recommendItemIds);
    List<Store> bestStores(String searchWord);
}

@Service
public class RecommendServiceImpl implements RecommendService {
    @Autowired
    RecommendRepository recommendRepository;

    @Override
    public void recommendStore(List<String> recommendItemIds) {
        for (String searchStoreId : recommendItemIds) {
            recommendRepository.recommendStores(searchStoreId);

            System.out.println(recommendRepository.recommendStores(searchStoreId).getStoreName());
        }
    }

    @Override
    public List<Store> bestStores(String searchWord){
        return recommendRepository.bestStores(searchWord);
    }



}


