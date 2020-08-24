package com.mobeom.local_currency.recommend;

import static com.mobeom.local_currency.store.QStore.store;
import static com.mobeom.local_currency.recommend.QConsume.consume;
import static com.mobeom.local_currency.recommend.QIndustry.industry;
import static com.mobeom.local_currency.favorites.QFavorites.favorites;
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
    IndustryStore fetchRecommendedStores(String searchWord);

    List<IndustryStore> fetchBestStore(double lat, double lng);

    List<IndustryStore> fetchStoreByIndustry(String searchIndustry, double lat, double lng);

    List<Consume> fetchIndustryRankByGenderAndAge(String gender, int ageGroup);

    List<Consume> fetchIndustryRankByAge(int age);

    List<Consume> fetchIndustryRankByGender(String gender);

    List<Consume> fetchIndustryRankByTotal();

    Store fetchRatedStore(String id);

    Store fetchOneFavStore(String id);

    List<IndustryStore> fetchMostFavoriteStores(double lat, double lng);

    List<IndustryStore> fetchBestRatedStores(double lat, double lng);

    List<IndustryStore> fetchMostFavStoresByIndustry(String industryName, double lat, double lng);

    List<IndustryStore> fetchBestRatedStoresByIndustry(String industryName, double lat, double lng);

    String fetchImg(IndustryStore store);

//    IndustryStore fetchAvgRating(IndustryStore oneStore);

}

@Repository
public class RecommendRepositoryImpl extends QuerydslRepositorySupport implements CustomRecommendRepository {
    private final JPAQueryFactory queryFactory;
    private final DataSource dataSource;


    RecommendRepositoryImpl(JPAQueryFactory queryFactory, DataSource dataSource) {
        super(Consume.class);
        this.queryFactory = queryFactory;
        this.dataSource = dataSource;
    }


    @Override //mahout을 통한 가맹점 추천
    public IndustryStore fetchRecommendedStores(String searchWord) {
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
                store.searchResultCount)
        )
                .from(store).where(store.id.eq(Long.valueOf(searchWord))).fetchFirst();

    }

    @Override //단순 가맹점 추천(서치 순)
    public List<IndustryStore> fetchBestStore(double lat, double lng) {
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
    public List<Consume> fetchIndustryRankByGenderAndAge(String gender, int ageGroup) {
        return queryFactory.selectFrom(consume).where(consume.genderCode.eq(gender), consume.ageGroup.eq(ageGroup))
                .orderBy(consume.amount.desc()).limit(7).fetch();
    }


    @Override // 연령으로 대분류 추천
    public List<Consume> fetchIndustryRankByAge(int age) {
        return queryFactory.select(Projections.fields(Consume.class, consume.ageGroup,
                consume.industryName)).from(consume)
                .where(consume.ageGroup.eq(age))
                .groupBy(consume.ageGroup, consume.industryName)
                .orderBy(consume.amount.sum().desc())
                .limit(7).fetch();
    }


    @Override
    public List<Consume> fetchIndustryRankByGender(String gender) {
        return queryFactory.select(Projections.fields(Consume.class, consume.genderCode,
                consume.industryName)).from(consume)
                .where(consume.genderCode.eq(gender))
                .groupBy(consume.genderCode, consume.industryName)
                .orderBy(consume.amount.sum().desc())
                .limit(7).fetch();

    }

    @Override
    public List<Consume> fetchIndustryRankByTotal() {
        return queryFactory.select(Projections.fields(Consume.class,
                consume.industryName)).from(consume)
                .groupBy(consume.industryName)
                .orderBy(consume.amount.sum().desc())
                .limit(7).fetch();
    }


    @Override
    public Store fetchRatedStore(String id) {
        return queryFactory.select(Projections.fields(Store.class,
                store.id, store.storeName)).from(store)
                .innerJoin(rating).on(rating.store.id.eq(store.id))
                .where(rating.user.id.eq(Long.valueOf(id)), store.localName.eq("의정부시"))
                        .fetchFirst();
    }

    @Override
    public Store fetchOneFavStore(String id){
        return queryFactory.select(Projections.fields(Store.class,
                store.id, store.storeName, store.mainCode)).from(store)
                .innerJoin(favorites).on(store.id.eq(favorites.store.id))
                .where(favorites.user.id.eq(Long.parseLong(id))).fetchFirst();
    }


    @Override
    public List<IndustryStore> fetchBestRatedStores(double lat, double lng) {
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
    public List<IndustryStore> fetchMostFavoriteStores(double lat, double lng) {
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
    public List<IndustryStore> fetchMostFavStoresByIndustry(String searchIndustry, double lat, double lng) {
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
    public List<IndustryStore> fetchBestRatedStoresByIndustry(String searchIndustry, double lat, double lng) {
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

    public String fetchImg(IndustryStore store){
        return queryFactory.select(industry.industryImageUrl).from(industry).where(industry.mainCode.eq(store.getMainCode())).fetchFirst();
    }

//    @Override
//    public IndustryStore fetchAvgRating(IndustryStore oneStore) {
//        return queryFactory.select(Projections.fields(IndustryStore.class, rating.starRating.avg().as("starRanking"))
//                )
//                .from(store)
//                .rightJoin(rating).on(store.id.eq(rating.store.id))
//                .groupBy(rating.store.id).fetchFirst();
//    }






}
