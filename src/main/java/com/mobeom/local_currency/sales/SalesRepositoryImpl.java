package com.mobeom.local_currency.sales;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


interface CustomSalesRepository{}
public class SalesRepositoryImpl extends QuerydslRepositorySupport implements CustomSalesRepository{
    @Autowired
    JPAQueryFactory query;

    SalesRepositoryImpl(){
        super(Sales.class);
    }
}
