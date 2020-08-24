package com.mobeom.local_currency.join;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PurchaseVO {
    private LocalDate salesDate;
    private int unitPrice;
    private LocalDate useDate;
    private boolean giftYn;
    private LocalDate cancelDate;
    private String currencyState;
    private String paymentName;
    private String localCurrencyVoucherName;
    private String recipientEmail;
    private long userId;
    private String userStringId;
    private String userName;
}
