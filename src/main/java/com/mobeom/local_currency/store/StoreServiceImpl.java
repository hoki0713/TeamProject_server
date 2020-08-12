package com.mobeom.local_currency.store;


import com.mobeom.local_currency.proxy.Box;
import com.mobeom.local_currency.proxy.JpaService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
interface StoreService extends JpaService<Store> {


    Object getUi();

    Object getMap(String clickedState);

    Optional<List<Store>> findAllStoreByName(String storeName);
}


@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository repository;
    private final Box<List<Store>> stores;

    public StoreServiceImpl(StoreRepository repository, Box<List<Store>> stores) {
        this.repository = repository;
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
    public Object getUi() {
        return repository.uiList();
    }

    @Override
    public Object getMap(String clickedState) {
        return (clickedState!="")?repository.findByLocal(clickedState):findAll();
    }

    @Override
    public Optional<List<Store>> findAllStoreByName(String storeName) {
        List<Store> storeList = repository.findAllByStoreName(storeName);
        return Optional.of(storeList);
    }
}
