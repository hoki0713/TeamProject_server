package com.mobeom.local_currency.voucher;

import javax.persistence.*;

@Entity
@Table(name = "local_currency_voucher")
public class LocalCurrencyVoucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "local_currency_voucher_id")
    private Long localCurrencyVoucherId;

    @Column(name = "local_name")
    private String localName;

    @Column(name = "local_currency_name")
    private String localCurrencyName;

    @Column(name = "voucher_value")
    private int voucherValue;
}
