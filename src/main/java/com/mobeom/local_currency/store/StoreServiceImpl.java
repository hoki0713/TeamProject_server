package com.mobeom.local_currency.store;


import com.mobeom.local_currency.admin.IndustryRepository;
import com.mobeom.local_currency.join.IndustryStore;
import com.mobeom.local_currency.proxy.JpaService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Component
interface StoreService extends JpaService<Store> {

    Optional<List<Store>> findAllStoreByName(String storeName);

    Object getSeveral(String searchWD);

    Object getStores(String s, String s1);

    List<Store> findStoreBySearchWord(String searchWord);

    Object getSome(String stateName, String category, int pageNow, int limitSize);

    Object getChatbotSearch(String searchWord, int pageSize);

    int getChatbotSearchCount(String searchWord);

    List<IndustryStore> getChatbotRecoMain(String lat, String lng);

    List<IndustryStore> getChatbotRank(String stateName);

    List<IndustryStore> getChatbotStarRank(String stateName);
}


@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository repository;

    public StoreServiceImpl(StoreRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Store> findById(String id) {
        return repository.findById(Long.parseLong(id));
    }

    @Override
    public Iterable<Store> findAll() {
        return repository.findAll();
    }

    @Override
    public int count() {
        return (int) repository.count();
    }

    @Override
    public void delete(String id) {
        repository.delete(findById(id).orElse(new Store()));
    }

    @Override
    public boolean exists(String id) {
        return repository.existsById(Long.parseLong(id));
    }

    @Override
    public Optional<List<Store>> findAllStoreByName(String storeName) {
        List<Store> storeList = repository.findAllByStoreName(storeName);
        return Optional.of(storeList);
    }

    @Override
    public Object getSeveral(String searchWD) {
        return repository.findSeveral(searchWD);
    }

    @Override
    public Object getStores(String s, String s1) {
        return repository.findByLatLng(s, s1);
    }

    @Override
    public List<Store> findStoreBySearchWord(String searchWord) {
        return repository.findSeveral(searchWord);
    }

    @Override
    public Object getSome(String stateName, String category, int pageNow, int limitSize) {
        return repository.findSome(stateName, category, pageNow, limitSize);
    }

    @Override
    public Object getChatbotSearch(String searchWord, int pageSize) {
        return repository.findChatbotSearch(searchWord,pageSize);
    }

    @Override
    public int getChatbotSearchCount(String searchWord) {
        return repository.findChatbotSearchCount(searchWord);
    }

    @Override
    public List<IndustryStore> getChatbotRecoMain(String lat, String lng) {
        return repository.findChatbotRecoMain(lat,lng);
    }

    @Override
    public List<IndustryStore> getChatbotRank(String stateName) {
        return repository.findChatbotRank(stateName);
    }

    @Override
    public List<IndustryStore> getChatbotStarRank(String stateName) {
        return repository.findChatbotStarRank(stateName);
    }


}
