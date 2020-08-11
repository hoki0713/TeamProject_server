package com.mobeom.local_currency.join;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@ToString
public class SalesVoucher {
    private String localCurrencyName;
    private int unitPrice;


}
