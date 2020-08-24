package com.mobeom.local_currency.voucher;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@NoArgsConstructor
@ToString
@Setter
@Getter
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
