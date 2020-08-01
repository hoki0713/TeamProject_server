package com.mobeom.local_currency.voucher;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

interface CustomedLocalCurrencyVoucherRepository {

}

public class LocalCurrencyVoucherRepositoryImpl extends QuerydslRepositorySupport implements CustomedLocalCurrencyVoucherRepository {
    @Autowired
    JPAQueryFactory queryFactory;

    LocalCurrencyVoucherRepositoryImpl() {
        super(LocalCurrencyVoucher.class);
    }
}
