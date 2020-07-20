package com.mobeom.local_currency.sales;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

interface SalesRepository extends JpaRepository<Sales, Long>, CustomedSalesRepository{}
interface CustomedSalesRepository{}
public class SalesRepositoryImpl extends QuerydslRepositorySupport implements CustomedSalesRepository{
    @Autowired
    JPAQueryFactory query;

    SalesRepositoryImpl(){
        super(Sales.class);
    }
}
