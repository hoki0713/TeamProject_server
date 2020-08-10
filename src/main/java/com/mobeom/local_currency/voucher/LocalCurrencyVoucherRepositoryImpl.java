package com.mobeom.local_currency.voucher;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

interface CustomLocalCurrencyVoucherRepository {

    List<LocalCurrencyVoucher> findAllByDefaultAddr(String defaultAddr);
}

public class LocalCurrencyVoucherRepositoryImpl extends QuerydslRepositorySupport implements CustomLocalCurrencyVoucherRepository {
    @Autowired
    JPAQueryFactory queryFactory;

    LocalCurrencyVoucherRepositoryImpl() {
        super(LocalCurrencyVoucher.class);
    }

    @Override
    public List<LocalCurrencyVoucher> findAllByDefaultAddr(String defaultAddr) {
        QLocalCurrencyVoucher qLocalCurrencyVoucher = QLocalCurrencyVoucher.localCurrencyVoucher;
        String[] splitAddress = defaultAddr.split(" ");
        List<LocalCurrencyVoucher> list =
                queryFactory.selectFrom(qLocalCurrencyVoucher).where(
                        qLocalCurrencyVoucher.localName.like(splitAddress[0]+" "+splitAddress[1])).fetch();
        return list;
    }
}
