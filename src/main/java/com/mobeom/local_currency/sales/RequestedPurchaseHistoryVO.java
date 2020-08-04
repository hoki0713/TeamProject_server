package com.mobeom.local_currency.sales;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestedPurchaseHistoryVO {
   private LocalDate salesDate;
   private int unitPrice;
   private LocalDate useDate;
   private boolean giftYn;
   private LocalDate cancelDate;
   private String currencyState;
   private String paymentName;
   private String localCurrencyVoucherName;
   private String recipientEmail;

   public RequestedPurchaseHistoryVO(LocalDate salesDate,
                                     int unitPrice, LocalDate useDate,
                                     boolean giftYn, LocalDate cancelDate,
                                     String currencyState,
                                     String paymentName,
                                     String localCurrencyVoucherName,
                                     String recipientEmail) {
      this.salesDate = salesDate;
      this.unitPrice = unitPrice;
      this.useDate = useDate;
      this.giftYn = giftYn;
      this.cancelDate = cancelDate;
      this.currencyState = currencyState;
      this.paymentName = paymentName;
      this.localCurrencyVoucherName = localCurrencyVoucherName;
      this.recipientEmail = recipientEmail;
   }
}
