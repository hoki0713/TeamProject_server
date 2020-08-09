package com.mobeom.local_currency.store;


import com.mobeom.local_currency.proxy.Box;
import com.mobeom.local_currency.proxy.JpaService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
interface StoreService extends JpaService<Store> {

    List<Store> getAll();

    Object getUi();
}


@Service
public class StoreServiceImpl implements StoreService {
    private final StoreRepository storeRepository;
    private final Box<List<Store>> stores;

    public StoreServiceImpl(StoreRepository storeRepository, Box<List<Store>> stores) {
        this.storeRepository = storeRepository;
        this.stores = stores;
    }


    @Override
    public Optional<Store> findById(String id) {
        return Optional.empty();
    }

    @Override
    public Iterable<Store> findAll() {
        return null;
    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public boolean exists(String id) {
        return false;
    }

    @Override
    public List<Store> getAll() {
        return storeRepository.findAll();
    }

    @Override
    public Object getUi() {
        return storeRepository.uiList();
    }
}
