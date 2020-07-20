package com.mobeom.local_currency.sales;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSales is a Querydsl query type for Sales
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSales extends EntityPathBase<Sales> {

    private static final long serialVersionUID = -1355891425L;

    public static final QSales sales = new QSales("sales");

    public final DateTimePath<java.time.LocalDateTime> cancelDate = createDateTime("cancelDate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> currencyState = createNumber("currencyState", Integer.class);

    public final NumberPath<Integer> giftYn = createNumber("giftYn", Integer.class);

    public final NumberPath<Integer> paymentCode = createNumber("paymentCode", Integer.class);

    public final StringPath paymentName = createString("paymentName");

    public final NumberPath<Integer> paymentTypeCode = createNumber("paymentTypeCode", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> salesDate = createDateTime("salesDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> salesId = createNumber("salesId", Long.class);

    public final NumberPath<Integer> unitPrice = createNumber("unitPrice", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> useDate = createDateTime("useDate", java.time.LocalDateTime.class);

    public QSales(String variable) {
        super(Sales.class, forVariable(variable));
    }

    public QSales(Path<? extends Sales> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSales(PathMetadata metadata) {
        super(Sales.class, metadata);
    }

}

