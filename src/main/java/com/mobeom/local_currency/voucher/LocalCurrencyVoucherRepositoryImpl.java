package com.mobeom.local_currency.voucher;

import static com.mobeom.local_currency.voucher.QLocalCurrencyVoucher.localCurrencyVoucher;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

interface CustomLocalCurrencyVoucherRepository {

    List<LocalCurrencyVoucher> findAllByDefaultAddr(String defaultAddr);

    LocalCurrencyVoucher findByLocalNameAndUnitPrice(String localName, int unitPrice);
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

    @Override
    public LocalCurrencyVoucher findByLocalNameAndUnitPrice(String localName, int unitPrice) {

        return queryFactory.select(Projections.fields(LocalCurrencyVoucher.class, localCurrencyVoucher.localCurrencyVoucherId))
                .from(localCurrencyVoucher)
                .where(localCurrencyVoucher.localName.endsWith(" "+localName)
                        ,localCurrencyVoucher.voucherValue.eq(unitPrice)).fetchFirst();
}}
