package com.mobeom.local_currency.store;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;


interface CustomStoreRepository {

    List<Store> findAllStoreByUserDefaultAddr(String defaultAddr);
}


public class StoreRepositoryImpl extends QuerydslRepositorySupport implements CustomStoreRepository {
    @Autowired
    JPAQueryFactory queryFactory;

    StoreRepositoryImpl() {
        super(Store.class);
    }

    @Override
    public List<Store> findAllStoreByUserDefaultAddr(String defaultAddr) {
        QStore qStore = QStore.store;
        //System.out.println(defaultAddr.chars().boxed().collect(toList()));
        //String fixedAddr = defaultAddr.replace((char)160, (char)32).trim();
        //System.out.println(fixedAddr.chars().boxed().collect(toList()));
        //String[] defaultAddrArr = fixedAddr.split(" ");
        //System.out.println(Arrays.asList(defaultAddrArr));
        List<Store> resultList = queryFactory.selectFrom(qStore).where(qStore.localName.like(defaultAddr)).fetch();
        return resultList;
    }

}
