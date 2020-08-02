package com.mobeom.local_currency.voucher;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

interface CustomLocalCurrencyVoucherRepository {

    String findbyDefaultAddr(String defaultAddr);
}

public class LocalCurrencyVoucherRepositoryImpl extends QuerydslRepositorySupport implements CustomLocalCurrencyVoucherRepository {
    @Autowired
    JPAQueryFactory queryFactory;

    LocalCurrencyVoucherRepositoryImpl() {
        super(LocalCurrencyVoucher.class);
    }

    @Override
    public String findbyDefaultAddr(String defaultAddr) {
        QLocalCurrencyVoucher qLocalCurrencyVoucher = QLocalCurrencyVoucher.localCurrencyVoucher;
        LocalCurrencyVoucher findOne =
                queryFactory.selectFrom(qLocalCurrencyVoucher)
                        .where(qLocalCurrencyVoucher.localName.eq(defaultAddr))
                        .fetchOne();
        String findLocalCurrencyName = findOne.getLocalCurrencyName();
        return findLocalCurrencyName;
    }
}
