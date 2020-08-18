package com.mobeom.local_currency.recommend;

import static com.mobeom.local_currency.store.QStore.store;
import static com.mobeom.local_currency.recommend.QGenderAge.genderAge;
import static com.mobeom.local_currency.recommend.QIndustry.industry;
import static com.mobeom.local_currency.favorites.QFavorites.favorites;
import static com.mobeom.local_currency.user.QUser.user;
import static com.mobeom.local_currency.rating.QRating.rating;


import com.mobeom.local_currency.join.IndustryStore;
import com.mobeom.local_currency.store.Store;
import com.querydsl.core.types.Projections;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

interface CustomRecommendRepository {
    IndustryStore recommendStores(String searchWord);

    List<IndustryStore> fetchByBestStore(double lat, double lng);

    List<IndustryStore> fetchStoreByIndustry(String searchIndustry, double lat, double lng);

    List<GenderAge> industryByGenderAndAge(String gender, int ageGroup);

    List<GenderAge> industryByAge(int age);

    List<GenderAge> industryByGender(String gender);

    List<GenderAge> industryByTotal();

    Store fetchedFavoriteStoreByUserId(String id);

    List<IndustryStore> fetchedMostFavoriteStores(double lat, double lng);

    List<IndustryStore> fetchedBestRatedStores(double lat, double lng);

    List<IndustryStore> fetchedMostFavStoresByIndustry(String industryName, double lat, double lng);

    List<IndustryStore> fetchedBestRatedStoresByIndustry(String industryName, double lat, double lng);

//    IndustryStore fetchAvgRating(IndustryStore oneStore);

}

@Repository
public class RecommendRepositoryImpl extends QuerydslRepositorySupport implements CustomRecommendRepository {
    private final JPAQueryFactory queryFactory;
    private final DataSource dataSource;


    RecommendRepositoryImpl(JPAQueryFactory queryFactory, DataSource dataSource) {
        super(GenderAge.class);
        this.queryFactory = queryFactory;
        this.dataSource = dataSource;
    }


    @Override //mahout을 통한 가맹점 추천
    public IndustryStore recommendStores(String searchWord) {
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
                industry.industryImageUrl.as("imgUrl"))
        )
                .from(store).innerJoin(industry)
                .on(store.storeTypeCode.eq(industry.industryCode))
                .fetchJoin().where(store.id.eq(Long.valueOf(searchWord))).fetchFirst();

    }

    @Override //단순 가맹점 추천(서치 순)
    public List<IndustryStore> fetchByBestStore(double lat, double lng) {
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
                industry.industryImageUrl.as("imgUrl"))
        ).from(store).innerJoin(industry)
                .on(store.storeTypeCode.eq(industry.industryCode))
                .fetchJoin().where(store.latitude.between(lat - 0.045, lat + 0.045),
                        store.longitude.between(lng - 0.06, lng + 0.06))
                .orderBy(store.searchResultCount.desc()).limit(7).fetch();
    }


    @Override //업종명으로 가맹점 찾기(img 연결된 ver)
    public List<IndustryStore> fetchStoreByIndustry(String searchIndustry, double lat, double lng) {
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
                industry.industryImageUrl.as("imgUrl"))
        )
                .from(store).innerJoin(industry)
                .on(store.storeTypeCode.eq(industry.industryCode))
                .fetchJoin().where(industry.mainCode.eq(searchIndustry),
                        store.latitude.between(lat - 0.045, lat + 0.045),
                        store.longitude.between(lng - 0.06, lng + 0.06))
                .orderBy(store.searchResultCount.desc()).limit(7).fetch();
    }

    @Override //성별 및 연령 입력시 대분류 안내
    public List<GenderAge> industryByGenderAndAge(String gender, int ageGroup) {
        return queryFactory.selectFrom(genderAge).where(genderAge.genderCode.eq(gender), genderAge.ageGroup.eq(ageGroup))
                .orderBy(genderAge.amount.desc()).limit(7).fetch();
    }


    @Override // 연령으로 대분류 추천
    public List<GenderAge> industryByAge(int age) {
        return queryFactory.select(Projections.fields(GenderAge.class, genderAge.ageGroup,
                genderAge.industryName)).from(genderAge)
                .where(genderAge.ageGroup.eq(age))
                .groupBy(genderAge.ageGroup, genderAge.industryName)
                .orderBy(genderAge.amount.sum().desc())
                .limit(7).fetch();
    }


    @Override
    public List<GenderAge> industryByGender(String gender) {
        return queryFactory.select(Projections.fields(GenderAge.class, genderAge.genderCode,
                genderAge.industryName)).from(genderAge)
                .where(genderAge.genderCode.eq(gender))
                .groupBy(genderAge.genderCode, genderAge.industryName)
                .orderBy(genderAge.amount.sum().desc())
                .limit(7).fetch();

    }

    @Override
    public List<GenderAge> industryByTotal() {
        return queryFactory.select(Projections.fields(GenderAge.class,
                genderAge.industryName)).from(genderAge)
                .groupBy(genderAge.industryName)
                .orderBy(genderAge.amount.sum().desc())
                .limit(7).fetch();
    }


    @Override
    public Store fetchedFavoriteStoreByUserId(String id) {
        return queryFactory.select(Projections.fields(Store.class,
                store.id, store.mainCode, store.storeName)).from(favorites)
                .innerJoin(store).on(store.id.eq(favorites.store.id))
                .innerJoin(user).on(user.id.eq(favorites.user.id)).fetchJoin()
                .where(user.id.eq(Long.valueOf(id)), store.localName.eq("의정부시")).
                        orderBy(store.searchResultCount.desc()).fetchFirst();
    }


    @Override
    public List<IndustryStore> fetchedBestRatedStores(double lat, double lng) {
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
                industry.industryImageUrl.as("imgUrl"),
                rating.starRating.avg().as("starRanking")))
        .from(store).innerJoin(industry).on(store.storeTypeCode.eq(industry.industryCode))
                .innerJoin(rating).on(store.id.eq(rating.store.id)).fetchJoin()
                .where(store.latitude.between(lat - 0.045, lat + 0.045),
                        store.longitude.between(lng - 0.06, lng + 0.06))
                .groupBy(rating.store.id).orderBy(rating.starRating.avg().desc(),
                        store.searchResultCount.desc()).limit(7).fetch();
    }

    @Override
    public List<IndustryStore> fetchedMostFavoriteStores(double lat, double lng) {
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
                .from(store).innerJoin(industry).on(store.storeTypeCode.eq(industry.industryCode))
                .innerJoin(favorites).on(store.id.eq(favorites.store.id)).fetchJoin()
                .where(store.latitude.between(lat - 0.045, lat + 0.045),
                        store.longitude.between(lng - 0.06, lng + 0.06))
                .groupBy(favorites.store).orderBy(favorites.store.count().desc()).limit(7).fetch();

    }

    @Override
    public List<IndustryStore> fetchedMostFavStoresByIndustry(String searchIndustry, double lat, double lng) {
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
        )).from(store).innerJoin(industry).on(store.storeTypeCode.eq(industry.industryCode))
                .innerJoin(favorites).on(store.id.eq(favorites.store.id)).fetchJoin()
                .where(store.latitude.between(lat - 0.045, lat + 0.045),
                        store.longitude.between(lng - 0.06, lng + 0.06),
                        store.mainCode.eq(searchIndustry))
                .groupBy(favorites.store).orderBy(favorites.store.count().desc()).limit(7).fetch();

    }

    @Override
    public List<IndustryStore> fetchedBestRatedStoresByIndustry(String searchIndustry, double lat, double lng) {
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
                industry.industryImageUrl.as("imgUrl"),
                rating.starRating.avg().as("starRanking")
        )).from(store).innerJoin(industry).on(store.storeTypeCode.eq(industry.industryCode))
                .innerJoin(rating).on(store.id.eq(rating.store.id)).fetchJoin()
                .where(store.latitude.between(lat - 0.045, lat + 0.045),
                        store.longitude.between(lng - 0.06, lng + 0.06),
                        store.mainCode.eq(searchIndustry))
                .groupBy(rating.store.id).orderBy(rating.starRating.avg().desc()).limit(7).fetch();

    }

//    @Override
//    public IndustryStore fetchAvgRating(IndustryStore oneStore) {
//        return queryFactory.select(Projections.fields(IndustryStore.class, rating.starRating.avg().as("starRanking"))
//                )
//                .from(store)
//                .leftJoin(rating).on(store.id.eq(rating.store.id))
//                .groupBy(rating.store.id).fetchFirst();
//    }






}
