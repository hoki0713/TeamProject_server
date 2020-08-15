package com.mobeom.local_currency.reportList;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportListRepository extends JpaRepository<ReportList, Long>, CustomReportRepository {

}
