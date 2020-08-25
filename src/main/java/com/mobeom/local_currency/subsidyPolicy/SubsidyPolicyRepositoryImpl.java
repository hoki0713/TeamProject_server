package com.mobeom.local_currency.subsidyPolicy;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import static com.mobeom.local_currency.subsidyPolicy.QSubsidyPolicy.subsidyPolicy;

import javax.sql.DataSource;

interface ISubsidyPolicyRepository{
    Object findFitPolicy(int userAge, boolean children);
}

@Repository
public class SubsidyPolicyRepositoryImpl extends QuerydslRepositorySupport implements ISubsidyPolicyRepository {
    private final JPAQueryFactory queryFactory;
    private final DataSource dataSource;


    SubsidyPolicyRepositoryImpl(JPAQueryFactory queryFactory, DataSource dataSource){
        super(SubsidyPolicy.class);
        this.queryFactory = queryFactory;
        this.dataSource = dataSource;
    }

    @Override
    public Object findFitPolicy(int userAge, boolean children) {
        Object result;
        if (children) {
            result = queryFactory.select(subsidyPolicy)
                    .from(subsidyPolicy)
                    .where(subsidyPolicy.condiAge.eq(0).or(subsidyPolicy.condiAge.eq(userAge)))
                    .fetch();
        } else {
            result = queryFactory.select(subsidyPolicy)
                    .from(subsidyPolicy)
                    .where(subsidyPolicy.condiChildrenAge.eq(0))
                    .where(subsidyPolicy.condiAge.eq(userAge))
                    .fetch();
        }
        return result;
    }
}
