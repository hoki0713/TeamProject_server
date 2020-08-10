package com.mobeom.local_currency.recommend;

import static com.mobeom.local_currency.store.QStore.store;
import static com.mobeom.local_currency.industry.QIndustry.industry;
import static com.mobeom.local_currency.board.QBoard.board;
import static com.mobeom.local_currency.post.QPost.post;
import static com.mobeom.local_currency.consume.QGender.gender;
import static com.querydsl.core.types.ExpressionUtils.count;



import com.mobeom.local_currency.consume.Gender;
import com.mobeom.local_currency.post.Post;
import com.mobeom.local_currency.store.QStore;
import com.mobeom.local_currency.store.Store;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.sql.DataSource;
import java.util.List;

interface CustomRecommendRepository {
    Store recommendStores(String searchWord);
    List<Store> fetchByBestStore(String searchWord);
    List<StoreVo> testRecommend(String storeName, String storeType);
    List<Store> genderRecommend();
    List<Gender> industryByGender(String searchWord);
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
    public Store recommendStores(String searchWord) {
        QStore store = QStore.store;
        JPAQueryFactory query = new JPAQueryFactory(getEntityManager());
        Store recommendStore = new Store();
        recommendStore = query.select(Projections.fields
                (Store.class, store.storeName, store.storeType, store.id)).from(store)
                .where(store.id.like(searchWord)).fetchOne(); // , store.starRanking
        return recommendStore;
    }

    @Override
    public List<Store> fetchByBestStore(String searchWord){
        return queryFactory.select(Projections.fields(Store.class, store.storeName, store.storeType)) // , store.starRanking
                .from(store).where(store.address.like("%" + searchWord + "%"))
                .orderBy(store.searchResultCount.desc()).limit(10).fetch();
    }


    @Override
    public List<StoreVo> testRecommend(String storeName, String storeType){
        return queryFactory
                .select(Projections.fields(StoreVo.class,
                        store.storeName.as("storeName"),
                        store.storeType.as("storeType"),
                        ExpressionUtils.as(
                                JPAExpressions.select(count(store.id))
                                        .from(store)
                                        .where(store.storeName.startsWith(storeName), store.storeType.contains(storeType)),
                                "passengerCounter")
                ))
                .from(store)
                .fetch();
    }

    @Override
    public List<Store> genderRecommend() {
        return from(store).innerJoin(industry).on(store.storeTypeCode.eq(industry.industryCode)).fetchJoin().where(industry.mainCode.endsWith("비영리")).fetch();
    }

    @Override
    public List<Gender> industryByGender(String searchWord){
        return queryFactory.select(Projections.fields(Gender.class,
                gender.industryName)).from(gender).where(gender.genderCode.eq(searchWord)).orderBy(gender.amount.desc()).limit(5).fetch();
    }

}
