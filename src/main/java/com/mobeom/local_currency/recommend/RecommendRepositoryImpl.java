package com.mobeom.local_currency.recommend;

import static com.mobeom.local_currency.store.QStore.store;
import com.mobeom.local_currency.consume.QConsume;
import com.mobeom.local_currency.industry.QIndustry;
import com.mobeom.local_currency.store.QStore;
import com.mobeom.local_currency.store.Store;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.sql.DataSource;
import java.util.List;

interface CustomRecommendRepository {
    Store recommendStores(String searchWord);
    List<Store> fetchByBestStore(String searchWord);

}

public class RecommendRepositoryImpl extends QuerydslRepositorySupport implements CustomRecommendRepository {
    private final JPAQueryFactory queryFactory;
    private final DataSource dataSource;


    RecommendRepositoryImpl(JPAQueryFactory queryFactory, DataSource dataSource) {
        super(Recommend.class);
        this.queryFactory = queryFactory;
        this.dataSource = dataSource;
    }

    @Override
    public List<Store> fetchByBestStore(String searchWord){
        return queryFactory.select(Projections.fields(Store.class, store.storeName, store.storeType, store.starRanking))
                .from(store).where(store.address.like("%" + searchWord + "%"))
                .orderBy(store.searchResultCount.desc()).limit(10).fetch();
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



    public List<Store> genderRecommend(){
        QStore store = QStore.store;
        QConsume consume = QConsume.consume;
        QIndustry industry = QIndustry.industry;

        JPAQueryFactory query = new JPAQueryFactory(getEntityManager());
        return null;
    }

}
