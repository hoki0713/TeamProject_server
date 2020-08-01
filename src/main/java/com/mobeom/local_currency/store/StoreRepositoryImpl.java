package com.mobeom.local_currency.store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface StoreRepository extends JpaRepository<Store,Long> , CustomedStoreRepository{}


interface CustomedStoreRepository
{
    public List<Store> findAll();
}


public class StoreRepositoryImpl extends QuerydslRepositorySupport implements CustomedStoreRepository {

    public StoreRepositoryImpl() {
        super(Store.class);
    }

    @Override
    public List<Store> findAll() {
        return null;
    }

}
