package com.mobeom.local_currency.statistic;

import com.mobeom.local_currency.user.QUser;
import com.mobeom.local_currency.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.*;

interface CustomStatisticRepository {

    Map<String, Long> getUserRegionStat(List<String> listOfRegion);
}

public class StatisticRepositoryImpl extends QuerydslRepositorySupport implements CustomStatisticRepository {
    @Autowired
    JPAQueryFactory queryFactory;

    StatisticRepositoryImpl() {
        super(User.class);
    }

    @Override
    public Map<String, Long> getUserRegionStat(List<String> listOfRegion) {
        QUser qUser = QUser.user;
        Map<String,Long> result = new HashMap<>();
        listOfRegion.forEach(regionName -> {
            Long numOfUser = queryFactory.selectFrom(qUser)
                    .where(qUser.defaultAddr.like("%"+regionName+"%"))
                    .fetchCount();
            result.put(regionName, numOfUser);
        });
        return result;
    }
}
