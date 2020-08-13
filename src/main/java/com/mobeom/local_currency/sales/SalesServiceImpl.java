package com.mobeom.local_currency.sales;

import com.amazonaws.services.kendra.model.DatabaseEngineType;
import com.mobeom.local_currency.user.User;
import com.mobeom.local_currency.user.UserRepository;
import com.mobeom.local_currency.voucher.LocalCurrencyVoucher;
import com.mobeom.local_currency.voucher.LocalCurrencyVoucherRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
interface SalesService {

    Optional<Map<Long, RequestedPurchaseHistoryVO>> getHistoryByUserId(long userId);

    Optional<Sales> findVoucher(long voucherId);

    Sales update(Sales selectedHistory);

    Sales addSalesRecode(String userId, Sales salesInfo);
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
    public Sales addSalesRecode(String userId, Sales salesInfo) {
        Optional<User> user = userRepository.findById(Long.parseLong(userId));
        Optional<LocalCurrencyVoucher> localCurrencyVoucher
                = localCurrencyVoucherRepository.findById
                (salesInfo.getLocalCurrencyVoucher().getLocalCurrencyVoucherId());
        Sales newSales=new Sales();
        newSales.setUser(user.get());
        newSales.setUnitPrice(salesInfo.getUnitPrice());
        newSales.setGiftYn(false);
        newSales.setCurrencyState("미사용");
        newSales.setPaymentName(salesInfo.getPaymentName());
        newSales.setLocalCurrencyVoucher(localCurrencyVoucher.get());
        return salesRepository.save(newSales);
    }
}
