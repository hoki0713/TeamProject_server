package com.mobeom.local_currency.store;

import com.mobeom.local_currency.rating.QRating;
import com.mobeom.local_currency.recommend.QIndustry;
import com.mobeom.local_currency.join.IndustryStore;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

interface IStoreRepository {
    List<Store> findAllStoreByUserDefaultAddr(String defaultAddr);
    List<Store> uiList();
    List<IndustryStore> findByLocal(String clickedState);
    List<Store> findAllByStoreName(String storeName);
    List<Store> findAllByLocalName(String localName, PageRequest pageRequest);
    List<Store> findSeveral(String searchWD);
    List<IndustryStore> findByLatLng(String s, String s1);
}

@Repository
public class StoreRepositoryImpl extends QuerydslRepositorySupport implements IStoreRepository {

    private final JPAQueryFactory queryFactory;
    private final DataSource dataSource;

    public StoreRepositoryImpl(JPAQueryFactory queryFactory, DataSource dataSource) {
        super(Store.class);
        this.queryFactory = queryFactory;
        this.dataSource = dataSource;
    }


    @Override
    public List<Store> uiList() {
        QStore qStore = QStore.store;
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        return queryFactory.select(qStore).from(qStore).where(qStore.localName.like("의정부시")).limit(200).fetch();
    }//은송 정리필요

    @Override
    public List<IndustryStore> findByLocal(String clickedState) {
        QStore store = QStore.store;
        QIndustry industry = QIndustry.industry;
        return queryFactory.select(Projections.fields(IndustryStore.class,
                store.id,
                store.storeName,
                store.storePhone,
                store.address,
                store.latitude,
                store.longitude,
                store.storeTypeCode,
                store.storeType,
                store.mainCode,
                store.searchResultCount,
                industry.industryImageUrl.as("imgUrl")
                )).from(store).innerJoin(industry)
                .on(store.storeTypeCode.eq(industry.industryCode))
                .fetchJoin()
                .where(store.localName.like('%'+clickedState+'%'))
                .orderBy(store.searchResultCount.desc()).limit(200).fetch();

    }//은송 findbymap

    @Override
    public List<Store> findAllByStoreName(String storeName) {
        QStore qStore = QStore.store;
        List<Store> storeList = queryFactory.selectFrom(qStore).where(qStore.storeName.like(storeName)).fetch();
        return storeList;
    }

    @Override
    public List<Store> findAllByLocalName(String localName, PageRequest pageRequest) {
        QStore qStore = QStore.store;
        if(localName.equals("양주시")) {
            return queryFactory.selectFrom(qStore).where(qStore.localName.like(localName)).limit(100).fetch();
        } else {
            return queryFactory.selectFrom(qStore).where(qStore.localName.like("%" + localName + "%")).limit(100).fetch();
        }

    }

    @Override
    public List<Store> findSeveral(String searchWD) {
        QStore qStore = QStore.store;
        return queryFactory.selectFrom(qStore)
                .where(qStore.storeName.like("%"+searchWD+"%"))
                .limit(3).fetch();
    }// 은송 findbestroute

    @Override
    public List<IndustryStore> findByLatLng(String s, String s1) {
        QStore qStore = QStore.store;
        QIndustry industry = QIndustry.industry;
        QRating rating = QRating.rating;
        double lat = Double.parseDouble(s);
        double lng = Double.parseDouble(s1);
        return queryFactory.select(Projections.fields(IndustryStore.class,
                qStore.id,
                qStore.storeName,
                qStore.storePhone,
                qStore.address,
                qStore.latitude,
                qStore.longitude,
                qStore.storeTypeCode,
                qStore.storeType,
                qStore.mainCode,
                qStore.searchResultCount,
                rating.starRating.avg(),
                industry.industryImageUrl.as("imgUrl")
        )).from(qStore).innerJoin(industry)
                .on(qStore.storeTypeCode.eq(industry.industryCode))
                .innerJoin(rating).on(qStore.id.eq(rating.store.id))
                .fetchJoin()
                .where(qStore.latitude.between(lat-0.045, lat+0.045))
                .where(qStore.longitude.between(lng-0.06, lng+0.06))
                .groupBy(rating.store.id)
                .orderBy(qStore.searchResultCount.desc()).limit(200).fetch();
    }//eunsong FindByMap when user Logined

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
