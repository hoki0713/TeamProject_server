package com.mobeom.local_currency.voucher;

import com.mobeom.local_currency.rating.Rating;
import com.mobeom.local_currency.sales.Sales;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "local_currency_voucher", cascade = CascadeType.ALL)
    private List<Sales> salesList;

}
