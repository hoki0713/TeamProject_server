package com.mobeom.local_currency.currency;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

interface CurrencyRepository extends JpaRepository<Currency, Long>, CustomedCurrencyRepository { }
interface CustomedCurrencyRepository {
    public List<Currency> findAll();
}
public class CurrencyRepositoryImpl extends QuerydslRepositorySupport implements CustomedCurrencyRepository {
    @Autowired
    JPAQueryFactory query;

    CurrencyRepositoryImpl() {
        super(Currency.class);
    }

    @Override
    public List<Currency> findAll() {
        return null;
    }
}
