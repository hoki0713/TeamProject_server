package com.mobeom.local_currency.store;

import static com.mobeom.local_currency.store.QStore.store;
import static com.mobeom.local_currency.admin.QIndustry.industry;
import static com.mobeom.local_currency.rating.QRating.rating;

import com.mobeom.local_currency.join.IndustryStore;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

interface IStoreRepository {
    List<Store> findAllStoreByUserDefaultAddr(String defaultAddr);

    List<Store> findAllByStoreName(String storeName);

    List<Store> findSeveral(String searchWD);

    List<IndustryStore> findByLatLng(String s, String s1);

    Object findSome(String stateName, String category, int pageNow, int limitSize);

    Object findChatbotSearch(String searchWord, int pageSize);

    int findChatbotSearchCount(String searchWord);

    List<IndustryStore> findChatbotRecoMain(String lat, String lng);

    List<IndustryStore> findChatbotRank(String stateName);

    List<IndustryStore> findChatbotStarRank(String stateName);
}

@Repository
public class StoreRepositoryImpl extends QuerydslRepositorySupport implements IStoreRepository {
    private final JPAQueryFactory queryFactory;

    public StoreRepositoryImpl(JPAQueryFactory queryFactory) {
        super(Store.class);
        this.queryFactory = queryFactory;
    }


    @Override
    public List<Store> findAllByStoreName(String storeName) {
        QStore qStore = QStore.store;
        List<Store> storeList = queryFactory.selectFrom(qStore).where(qStore.storeName.like(storeName)).fetch();
        return storeList;
    }

    @Override
    public List<Store> findSeveral(String searchWD) {
        return queryFactory.selectFrom(store)
                .where(store.storeName.like("%" + searchWD + "%"))
                .limit(3).fetch();
    }

    @Override
    public List<IndustryStore> findByLatLng(String s, String s1) {
        double lat = Double.parseDouble(s);
        double lng = Double.parseDouble(s1);
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
                rating.starRating.avg().as("starRanking"),
                industry.industryImageUrl.as("imgUrl")
        )).from(store).innerJoin(industry)
                .on(store.storeTypeCode.eq(industry.industryCode))
                .innerJoin(rating).on(store.id.eq(rating.store.id))
                .fetchJoin()
                .where(store.latitude.between(lat - 0.045, lat + 0.045))
                .where(store.longitude.between(lng - 0.06, lng + 0.06))
                .groupBy(rating.store.id)
                .orderBy(store.searchResultCount.desc()).limit(200).fetch();
    }

    @Override
    public Object findSome(String stateName, String category, int pageNow, int limitSize) {
        if (stateName.equals("시/군")) {
            stateName = "";
        }
        if (category.equals("업종")) {
            category = "";
        }

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
                industry.industryImageUrl.as("imgUrl")))
                .from(store).innerJoin(industry)
                .on(store.storeTypeCode.eq(industry.industryCode))
                .groupBy(store.id)
                .orderBy(store.id.asc())
                .offset(pageNow)
                .limit(limitSize).fetch();
    }

    @Override
    public Object findChatbotSearch(String searchWord, int pageSize) {
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
                industry.industryImageUrl.as("imgUrl")))
                .from(store).innerJoin(industry)
                .on(store.storeTypeCode.eq(industry.industryCode))
                .groupBy(store.id)
                .orderBy(store.id.asc())
                .where(store.storeName.like("%" + searchWord + "%"))
                .limit(pageSize).fetch();
    }

    @Override
    public int findChatbotSearchCount(String searchWord) {
        return (int)queryFactory.select(store.count())
                .from(store).innerJoin(industry)
                .on(store.storeTypeCode.eq(industry.industryCode))
                .groupBy(store.id)
                .orderBy(store.id.asc())
                .where(store.storeName.like("%" + searchWord + "%"))
                .fetchCount();

    }

    @Override
    public List<IndustryStore> findChatbotRecoMain(String lat, String lng) {
        double latitude = Double.parseDouble(lat);
        double longitude = Double.parseDouble(lng);
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
                rating.starRating.avg().as("starRanking"),
                industry.industryImageUrl.as("imgUrl")
        )).from(store).innerJoin(industry)
                .on(store.storeTypeCode.eq(industry.industryCode))
                .innerJoin(rating).on(store.id.eq(rating.store.id))
                .fetchJoin()
                .where(store.latitude.between(latitude - 0.045, latitude + 0.045))
                .where(store.longitude.between(longitude - 0.06, longitude + 0.06))
                .groupBy(rating.store.id)
                .orderBy(store.searchResultCount.desc()).limit(10).fetch();
    }

    @Override
    public List<IndustryStore> findChatbotRank(String stateName) {
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
                rating.starRating.avg().as("starRanking"),
                industry.industryImageUrl.as("imgUrl")
        )).from(store).innerJoin(industry)
                .on(store.storeTypeCode.eq(industry.industryCode))
                .innerJoin(rating).on(store.id.eq(rating.store.id))
                .fetchJoin()
                .where(store.address.like("%"+stateName+"%"))
                .groupBy(rating.store.id)
                .orderBy(store.searchResultCount.desc()).limit(50).fetch();
    }

    @Override
    public List<IndustryStore> findChatbotStarRank(String stateName) {
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
                rating.starRating.avg().as("starRanking"),
                industry.industryImageUrl.as("imgUrl")
        )).from(store).innerJoin(industry)
                .on(store.storeTypeCode.eq(industry.industryCode))
                .innerJoin(rating).on(store.id.eq(rating.store.id))
                .fetchJoin()
                .where(store.address.like("%"+stateName+"%"))
                .groupBy(rating.store.id)
                .orderBy(rating.starRating.avg().desc())
                .limit(50).fetch();
    }

    @Override
    public List<Store> findAllStoreByUserDefaultAddr(String defaultAddr) {
        List<Store> resultList = queryFactory.selectFrom(store).where(store.localName.like(defaultAddr)).fetch();
        return resultList;
    }
}
