package com.mobeom.local_currency.recommend;

import static com.mobeom.local_currency.store.QStore.store;
import static com.mobeom.local_currency.recommend.QGenderAge.genderAge;
import static com.mobeom.local_currency.recommend.QIndustry.industry;


import com.mobeom.local_currency.join.IndustryStore;
import com.mobeom.local_currency.store.QStore;
import com.mobeom.local_currency.store.Store;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.sql.DataSource;
import java.util.List;

interface CustomRecommendRepository {
    IndustryStore recommendStores(String searchWord);

    List<Store> fetchByBestStore(String searchLocalWord);

    //    List<StoreVo> testRecommend(String storeName, String storeType);
    List<IndustryStore> fetchStoreByIndustry(String searchIndustry, String town);

    List<GenderAge> industryByGenderAndAge(String searchWord, int age);

    List<GenderAge> industryByAge(int age);

    List<GenderAge> industryByGender(String gender);
}

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
                store.storeName.as("storeName"),
                store.mainCode.as("mainCode"),
                industry.industryImageUrl.as("imgUrl"),
                store.storeType.as("storeType"),
                store.localName.as("localName"),
                store.address.as("address"))
        )
                .from(store).innerJoin(industry)
                .on(store.storeTypeCode.eq(industry.industryCode))
                .fetchJoin().where(store.id.eq(Long.valueOf(searchWord))).fetchOne();

    }

    @Override //단순 가맹점 추천(서치 순)
    public List<Store> fetchByBestStore(String searchLocalWord) {
        return queryFactory.select(Projections.fields(Store.class, store.storeName, store.storeType))
                .from(store).where(store.address.contains(searchLocalWord))
                .orderBy(store.searchResultCount.desc()).limit(10).fetch();
    }


//    @Override //서브쿼리 예제
//    public List<StoreVo> testRecommend(String storeName, String storeType){
//        return queryFactory
//                .select(Projections.fields(StoreVo.class,
//                        store.storeName.as("storeName"),
//                        store.storeType.as("storeType"),
//                        ExpressionUtils.as(
//                                JPAExpressions.select(count(store.id))
//                                        .from(store)
//                                        .where(store.storeName.startsWith(storeName), store.storeType.contains(storeType)),
//                                "passengerCounter")
//                ))
//                .from(store)
//                .fetch();
//    }

    @Override //업종명으로 가맹점 찾기(img 연결된 ver)
    public List<IndustryStore> fetchStoreByIndustry(String searchIndustry, String town) {
        return queryFactory.select(Projections.fields(IndustryStore.class,
                store.storeName.as("storeName"),
                store.mainCode.as("mainCode"),
                industry.industryImageUrl.as("imgUrl"),
                store.storeType.as("storeType"),
                store.localName.as("localName"),
                store.address.as("address"))
        )
                .from(store).innerJoin(industry)
                .on(store.storeTypeCode.eq(industry.industryCode))
                .fetchJoin().where(industry.mainCode.contains(searchIndustry), store.address.contains(town))
                .orderBy(store.searchResultCount.desc()).limit(7).fetch();
    }
    //고양시도 검색되는데 상관 없나... 헷갈린다.

    @Override //성별 및 연령 입력시 대분류 안내
    public List<GenderAge> industryByGenderAndAge(String searchWord, int age) {
        return queryFactory.selectFrom(genderAge).where(genderAge.genderCode.eq(searchWord), genderAge.ageGroup.eq(age))
                .orderBy(genderAge.amount.desc()).distinct().limit(7).fetch();
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
//    SELECT age_group, industry_name, SUM(amount) AS total
//    FROM consume
//    GROUP BY age_group, industry_name
//    ORDER BY total DESC


    @Override
    public List<GenderAge> industryByGender(String gender) {
        return queryFactory.select(Projections.fields(GenderAge.class, genderAge.genderCode,
                genderAge.industryName, genderAge.amount.sum())).from(genderAge)
                .where(genderAge.genderCode.eq(gender))
                .groupBy(genderAge.genderCode, genderAge.industryName)
                .orderBy(genderAge.amount.sum().desc())
                .limit(7).fetch();

    }

}
