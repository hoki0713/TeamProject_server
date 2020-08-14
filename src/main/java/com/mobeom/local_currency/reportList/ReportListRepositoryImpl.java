package com.mobeom.local_currency.reportList;

import static com.mobeom.local_currency.reportList.QReportList.reportList;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.Optional;

interface CustomReportRepository {

    Optional<ReportList> findByStoreId(Long storeId);
}

@Repository
public class ReportListRepositoryImpl extends QuerydslRepositorySupport implements CustomReportRepository {

    private final JPAQueryFactory queryFactory;

    public ReportListRepositoryImpl(JPAQueryFactory queryFactory) {
        super(ReportList.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Optional<ReportList> findByStoreId(Long storeId) {
        ReportList report = queryFactory
                .selectFrom(reportList)
                .where(reportList.store.id.eq(storeId))
                .fetchOne();
        return Optional.ofNullable(report);
    }
}
