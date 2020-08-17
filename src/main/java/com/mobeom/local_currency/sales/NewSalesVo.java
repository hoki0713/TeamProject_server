package com.mobeom.local_currency.sales;

import lombok.Data;



@Data
public class NewSalesVo {
    private String userId, localName, paymentName;
    private int unitPrice;
}
