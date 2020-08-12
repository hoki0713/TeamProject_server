package com.mobeom.local_currency.sales;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
interface SalesService {

    Optional<Map<Long, RequestedPurchaseHistoryVO>> getHistoryByUserId(long userId);

    Optional<Sales> findVoucher(long voucherId);

    Sales update(Sales selectedHistory);
}

@Service
@AllArgsConstructor
public class SalesServiceImpl implements SalesService {
    private final SalesRepository salesRepository;

    @Override
    public Optional<Map<Long, RequestedPurchaseHistoryVO>> getHistoryByUserId(long userId) {
        Map<Long, RequestedPurchaseHistoryVO> historyVOMap = salesRepository.findAllSalesRecordsByUserId(userId);
        return Optional.ofNullable(historyVOMap);
    }

    @Override
    public Optional<Sales> findVoucher(long voucherId) {
        return salesRepository.findById(voucherId);
    }

    @Override
    public Sales update(Sales selectedHistory) {
        return salesRepository.save(selectedHistory);
    }
}
