package com.mobeom.local_currency.recommend;

import com.mobeom.local_currency.store.QStore;
import com.mobeom.local_currency.store.Store;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.List;

interface CustomRecommendRepository {
    Store recommendStores(String searchWord);

    List<Store> bestStores(String searchWord);

}

public class RecommendRepositoryImpl extends QuerydslRepositorySupport implements CustomRecommendRepository {
    @Autowired
    JPAQueryFactory query;

    RecommendRepositoryImpl() {
        super(Recommend.class);
    }

    @Override
    public Store recommendStores(String searchWord) {
        QStore store = QStore.store;
        JPAQueryFactory query = new JPAQueryFactory(getEntityManager());
        Store recommendStore = new Store();
        recommendStore = query.select(Projections.fields
                (Store.class, store.storeName, store.storeType, store.starRanking, store.id)).from(store)
                .where(store.id.like(searchWord)).fetchOne();
        return recommendStore;
    }

    @Override
    public List<Store> bestStores(String searchWord) {
        QStore store = QStore.store;
        JPAQueryFactory query = new JPAQueryFactory(getEntityManager());
        List<Store> bestStores;
        bestStores = query.select(Projections.fields(Store.class, store.storeName, store.storeType, store.starRanking))
                .from(store).where(store.address.like("%" + searchWord + "%"))
                .orderBy(store.searchResultCount.desc()).limit(10).fetch();


        return bestStores;
    }

}
