package com.mobeom.local_currency.reportList;

import com.mobeom.local_currency.store.Store;
import com.mobeom.local_currency.store.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

interface ReportListService {

    ReportList createReport(Long storeId);
}

@Service
public class ReportListServiceImpl implements ReportListService{

    private final ReportListRepository reportListRepository;
    private final StoreRepository storeRepository;

    public ReportListServiceImpl(ReportListRepository reportListRepository, StoreRepository storeRepository) {
        this.reportListRepository = reportListRepository;
        this.storeRepository = storeRepository;
    }

    @Override
    public ReportList createReport(Long storeId) {
        Optional<Store> reportedStore = storeRepository.findById(storeId);
        Optional<ReportList> findReport = reportListRepository.findByStoreId(reportedStore.get().getId());
        if(findReport.isPresent()) {
            ReportList selectedReport = findReport.get();
            int addReportedCount = selectedReport.getReportedCount() + 1;
            selectedReport.setReportedCount(addReportedCount);
            return reportListRepository.save(selectedReport);
        } else {
            ReportList newReport = new ReportList();
            newReport.setStore(reportedStore.get());
            newReport.setReportedCount(1);
            ReportList savedReport = reportListRepository.save(newReport);
            return savedReport;
        }
    }
}
