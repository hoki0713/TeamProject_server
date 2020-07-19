package com.mobeom.local_currency.local_currency;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

interface LocalCurrencyRepository extends JpaRepository<LocalCurrency, Long>, LocalCurrencyService {}

interface LocalCurrencyService {

}

public class LocalCurrencyRepositoryImpl extends QuerydslRepositorySupport implements LocalCurrencyService {
    @Autowired
    JPAQueryFactory query;

    LocalCurrencyRepositoryImpl() {
        super(LocalCurrency.class);
    }
}
