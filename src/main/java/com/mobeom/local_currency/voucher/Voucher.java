package com.mobeom.local_currency.voucher;

import com.mobeom.local_currency.voucher.Voucher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity @Getter @Setter @ToString @NoArgsConstructor
@Table(name = "voucher")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_id",nullable = false) private Long id;

    @Column(name = "local_currency_id",nullable = false) private int localCurrencyId;

    @Column(name = "currency_type", nullable = false) private int currencyType;

  /*  @ManyToOne
    @JoinColumn(name = "currency_id")
    private Currency currency;*/



}
