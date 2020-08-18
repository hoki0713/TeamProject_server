package com.mobeom.local_currency.join;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
public class SalesVoucherUser {
    private String localCurrencyName,localName,userId,currencyState,name;
    private int unitPrice;
    private Long localCurrencyVoucherId,salesId;
    private LocalDate salesDate,cancelDate,useDate;

}
