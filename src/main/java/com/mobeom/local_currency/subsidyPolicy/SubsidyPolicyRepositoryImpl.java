package com.mobeom.local_currency.subsidyPolicy;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

interface SubsidyPolicyRepository extends JpaRepository<SubsidyPolicy, Long>, CustomSubsidyPolicyRepository {}
interface CustomSubsidyPolicyRepository {}
public class SubsidyPolicyRepositoryImpl extends QuerydslRepositorySupport implements CustomSubsidyPolicyRepository {
    @Autowired
    JPAQueryFactory query;

    SubsidyPolicyRepositoryImpl(){
        super(SubsidyPolicy.class);
    }
}
