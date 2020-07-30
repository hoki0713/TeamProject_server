package com.mobeom.local_currency.statistic;

import com.mobeom.local_currency.user.QUser;
import com.mobeom.local_currency.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.*;

interface CustomedStatisticRepository {

    Optional<List<Map<?,?>>> getUserRegionStat(List<String> listOfRegion);
}

public class StatisticRepositoryImpl extends QuerydslRepositorySupport implements CustomedStatisticRepository {
    @Autowired
    JPAQueryFactory queryFactory;

    StatisticRepositoryImpl() {
        super(User.class);
    }

    @Override
    public Optional<List<Map<?, ?>>> getUserRegionStat(List<String> listOfRegion) {
        QUser qUser = QUser.user;
        List<Map<?,?>> resultList = new ArrayList<>();
        for (int i = 0; i < listOfRegion.size(); i++) {
            Map<String, Integer> regionAndCount = new HashMap<>();
            List<?> listOfUser = queryFactory.selectFrom(qUser).where(qUser.defaultAddr.like("%"+listOfRegion.get(i)+"%")).fetch();
            regionAndCount.put(listOfRegion.get(i), listOfUser.size());
            resultList.add(regionAndCount);
        }
        return Optional.of(resultList);
    }
}
