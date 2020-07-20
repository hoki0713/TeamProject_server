package com.mobeom.local_currency.subsidyPolicy;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

interface SubsidyPolicyRepository extends JpaRepository<SubsidyPolicy, Long>, CustomedSubsidyPolicyRepository{}
interface CustomedSubsidyPolicyRepository{}
public class SubsidyPolicyRepositoryImpl extends QuerydslRepositorySupport implements CustomedSubsidyPolicyRepository{
    @Autowired
    JPAQueryFactory query;

    SubsidyPolicyRepositoryImpl(){
        super(SubsidyPolicy.class);
    }
}
