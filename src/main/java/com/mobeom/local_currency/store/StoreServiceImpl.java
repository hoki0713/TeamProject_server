package com.mobeom.local_currency.store;


import com.mobeom.local_currency.proxy.Box;
import com.mobeom.local_currency.proxy.JpaService;
import com.mobeom.local_currency.recommend.Industry;
import com.mobeom.local_currency.recommend.IndustryRepository;
import com.mobeom.local_currency.reportList.ReportList;
import com.mobeom.local_currency.reportList.ReportListRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.toMap;

@Component
interface StoreService extends JpaService<Store> {




    Optional<List<Store>> findAllStoreByName(String storeName);

    Map<Long, StoresVO> getAllStoresByLocalName(String localName);

    Object getSeveral(String searchWD);

    Object getStores(String s, String s1);

    List<Store> findStoreBySearchWord(String searchWord);

    Object getSome(String stateName, String category, int pageNow,int limitSize);
}


@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository repository;
    private final IndustryRepository industryRepository;
    private final ReportListRepository reportListRepository;
    private final Box<List<Store>> stores;

    public StoreServiceImpl(StoreRepository repository, IndustryRepository industryRepository, ReportListRepository reportListRepository, Box<List<Store>> stores) {
        this.repository = repository;
        this.industryRepository = industryRepository;
        this.reportListRepository = reportListRepository;
        this.stores = stores;
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
    public Map<Long, StoresVO> getAllStoresByLocalName(String localName) {
        Map<Long, StoresVO> storesMap = new HashMap<>();
        List<Store> stores = repository.findAllByLocalName(localName, PageRequest.of(0, 20));
        List<Industry> industries = industryRepository.findAll();
        List<ReportList> reports = reportListRepository.findAll();
        Map<Integer, String> industryImgUrls = industries.stream().collect(toMap(Industry::getIndustryCode, Industry::getIndustryImageUrl));
        Map<Store, Integer> storeReportCountMap = reports.stream().collect(toMap(ReportList::getStore, ReportList::getReportedCount));
        stores.forEach(store -> {
            StoresVO oneStore = new StoresVO();
            oneStore.setStoreName(store.getStoreName());
            oneStore.setStoreType(store.getStoreType());
            oneStore.setStorePhone(store.getStorePhone());
            oneStore.setAddress(store.getAddress());
            oneStore.setLatitude(store.getLatitude());
            oneStore.setLongitude(store.getLongitude());
            oneStore.setImgUrl(industryImgUrls.get(store.getStoreTypeCode()));
            if(storeReportCountMap.containsKey(store)) {
                oneStore.setReportedCount(storeReportCountMap.get(store));
            }
            storesMap.put(store.getId(), oneStore);
        });
        return storesMap;
    }

    @Override
    public Object getSeveral(String searchWD) {
        return repository.findSeveral(searchWD);
    }

    @Override
    public Object getStores(String s, String s1) {
        return repository.findByLatLng(s,s1);
    }

    @Override
    public List<Store> findStoreBySearchWord(String searchWord) {
        return repository.findSeveral(searchWord);
    }

    @Override
    public Object getSome(String stateName, String category, int pageNow,int limitSize) {
        return repository.findSome(stateName,category,pageNow, limitSize);
    }
}
