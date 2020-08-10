package com.mobeom.local_currency.subsidyPolicy;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

interface ISubsidyPolicyRepository{}



@Repository
public class SubsidyPolicyRepositoryImpl extends QuerydslRepositorySupport implements ISubsidyPolicyRepository {
    private final JPAQueryFactory queryFactory;
    private final DataSource dataSource;


    SubsidyPolicyRepositoryImpl(JPAQueryFactory queryFactory, DataSource dataSource){
        super(SubsidyPolicy.class);
        this.queryFactory = queryFactory;
        this.dataSource = dataSource;
    }
}
