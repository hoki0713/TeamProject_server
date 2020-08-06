package com.mobeom.local_currency.store;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
interface CustomStoreRepository
{
    Optional<Store> findByAll(String searchWD);
    // by store_name, store_type, local_name, road_address, store_phone
    List<Store> uiList();

}

@Component
public class StoreRepositoryImpl extends QuerydslRepositorySupport implements CustomStoreRepository {



    public StoreRepositoryImpl() {
        super(Store.class);
    }



    @Override
    public Optional<Store> findByAll(String searchWD) {


        return Optional.empty();
    }

    @Override
    public List<Store> uiList() {
        QStore qStore = QStore.store;
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        return queryFactory.select(qStore).from(qStore).where(qStore.localName.like("의정부시")).limit(200).fetch();

    }

}
