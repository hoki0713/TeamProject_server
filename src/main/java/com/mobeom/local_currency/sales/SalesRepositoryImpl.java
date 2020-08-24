package com.mobeom.local_currency.sales;

import com.mobeom.local_currency.join.RequestedPurchaseHistoryVO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.*;


interface CustomSalesRepository{

    Map<Long, RequestedPurchaseHistoryVO> findAllSalesRecordsByUserId(long userId);
}

public class SalesRepositoryImpl extends QuerydslRepositorySupport implements CustomSalesRepository{
    @Autowired
    JPAQueryFactory queryFactory;

    SalesRepositoryImpl(){
        super(Sales.class);
    }

    @Override
    public Map<Long, RequestedPurchaseHistoryVO> findAllSalesRecordsByUserId(long userId) {
        QSales qSales = QSales.sales;
        Map<Long, RequestedPurchaseHistoryVO> resultMap = new HashMap<>();

        List<Sales> salesList =
                queryFactory.selectFrom(qSales)
                    .where(qSales.user.id.eq(userId)).fetch();

        salesList.forEach((oneSalesRecord) ->
                resultMap.put(oneSalesRecord.getSalesId(),
                        new RequestedPurchaseHistoryVO(
                        oneSalesRecord.getSalesDate(),
                        oneSalesRecord.getUnitPrice(),
                        oneSalesRecord.getUseDate(),
                        oneSalesRecord.isGiftYn(),
                        oneSalesRecord.getCancelDate(),
                        oneSalesRecord.getCurrencyState(),
                        oneSalesRecord.getPaymentName(),
                        oneSalesRecord.getLocalCurrencyVoucher().getLocalCurrencyName(),
                        oneSalesRecord.getRecipientEmail()
                )));
        return resultMap;
    }
}
