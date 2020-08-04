package com.mobeom.local_currency.store;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


interface CustomStoreRepository
{
     List<Store> findAll();
     Map<String,Long> localsTotal(String localSelect);
     Map<String,Long> storeTypeLocal();
}


public class StoreRepositoryImpl extends QuerydslRepositorySupport implements CustomStoreRepository {

    public StoreRepositoryImpl() {
        super(Store.class);
    }

    @Autowired
    JPAQueryFactory query;

    @Override
    public List<Store> findAll() {
        return null;
    }

    @Override
    public Map<String, Long> localsTotal(String localSelect) {
        QStore store = QStore.store;
        Map<String,Long> localStoreChart = new HashMap<>();




        Long num = query.selectFrom(store).where(store.address.like("%"+localSelect+"%")).fetchCount();
//        for(int i=0;i<local.length;i++){
//            Long num = query.selectFrom(store).where(store.address.like("%"+local[i]+"%")).fetchCount();
//            localStoreChart.put(local[i],num);
//        }

        System.out.println(localStoreChart);

        localStoreChart.put("local",num);

        return localStoreChart;
    }

    @Override
    public Map<String, Long> storeTypeLocal() {

        QStore store = QStore.store;

        Map<String,Long> storeType = new HashMap<>();


       Long storeNum= query.selectFrom(store).where(store.storeTypeCode.like("3099").and(store.address.like("%"+"고양"+"%"))).fetchCount();
       List<?> list = query.selectFrom(store).where(store.storeTypeCode.like("3099").and(store.address.like("%"+"고양"+"%"))).fetch();

        System.out.println("storenum-"+storeNum);
        System.out.println(list.toString());

        storeType.put("a",storeNum);

        return storeType;
    }

}
