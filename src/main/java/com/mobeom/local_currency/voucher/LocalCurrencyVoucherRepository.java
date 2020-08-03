package com.mobeom.local_currency.voucher;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface LocalCurrencyVoucherRepository extends JpaRepository<LocalCurrencyVoucher, Long>, CustomLocalCurrencyVoucherRepository {

}
