package com.mobeom.local_currency.sales;


import com.mobeom.local_currency.user.User;
import com.mobeom.local_currency.user.UserRepository;
import com.mobeom.local_currency.voucher.LocalCurrencyVoucher;
import com.mobeom.local_currency.voucher.LocalCurrencyVoucherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Component
interface SalesService {

    Optional<Map<Long, RequestedPurchaseHistoryVO>> getHistoryByUserId(long userId);

    Optional<Sales> findVoucher(long voucherId);

    Sales update(Sales selectedHistory);

    void createSalesRecode(String userId, NewSalesVo salesInfo);
}

@Service
@AllArgsConstructor
public class SalesServiceImpl implements SalesService {
    private final SalesRepository salesRepository;
    private final UserRepository userRepository;
    private final LocalCurrencyVoucherRepository localCurrencyVoucherRepository;

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

    @Override
    public void createSalesRecode(String id, NewSalesVo salesInfo) {
        Sales newSales = new Sales();
        Optional<User> oneUser = userRepository.findById(Long.parseLong(id));
        newSales.setUser(oneUser.get());
        newSales.setCurrencyState("미사용");
        newSales.setPaymentName(salesInfo.getPaymentName());
        newSales.setUnitPrice(salesInfo.getUnitPrice());
        newSales.setLocalCurrencyVoucher(localCurrencyVoucherRepository.findByLocalNameAndUnitPrice(
                salesInfo.getLocalName(), salesInfo.getUnitPrice()
        ));
        salesRepository.save(newSales);
    }
}
