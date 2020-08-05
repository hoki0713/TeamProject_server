package com.mobeom.local_currency.store;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
interface CustomStoreRepository
{
    Optional<Store> findByAll(String searchWD);
    // by store_name, store_type, local_name, road_address, store_phone

}

@Component
public class StoreRepositoryImpl extends QuerydslRepositorySupport implements CustomStoreRepository {

    @Autowired
    JPAQueryFactory queryFactory;

    public StoreRepositoryImpl() {
        super(Store.class);
    }



    @Override
    public Optional<Store> findByAll(String searchWD) {


        return Optional.empty();
    }

}
