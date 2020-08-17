package com.mobeom.local_currency.join;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
public class SalesVoucher {
    private String localCurrencyName,localName;
    private int unitPrice;
    private Long localCurrencyVoucherId;
    private LocalDate salesDate;

}
