package com.mobeom.local_currency.store;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;
import java.util.List;

interface IStoreRepository {
    List<Store> findAllStoreByUserDefaultAddr(String defaultAddr);
    List<Store> uiList();
    List<Store> findByLocal(String clickedState);
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
    }

    @Override
    public List<Store> findByLocal(String clickedState) {
        QStore qStore = QStore.store;
        JPAQueryFactory queryFactory = new JPAQueryFactory(getEntityManager());
        return queryFactory.select(qStore).from(qStore).where(qStore.localName.like('%'+clickedState+'%')).limit(200).fetch();

    }

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
