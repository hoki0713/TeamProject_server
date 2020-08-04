package com.mobeom.local_currency.recommend;

import com.mobeom.local_currency.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
interface RecommendService {
    List<Store> recommendStore(List<String> recommendItemIds);
    List<Store> bestStores(String searchWord);
}

@Service
public class RecommendServiceImpl implements RecommendService {
    @Autowired
    RecommendRepository recommendRepository;

    @Override
    public List<Store> recommendStore(List<String> recommendItemIds) {
        for (String searchStoreId : recommendItemIds) {
            recommendRepository.recommendStores(searchStoreId);

            recommendRepository.recommendStores(searchStoreId).getStoreName();
        }

        return null;
    }

    @Override
    public List<Store> bestStores(String searchWord){
        return recommendRepository.bestStores(searchWord);
    }



}


