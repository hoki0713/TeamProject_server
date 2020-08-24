package com.mobeom.local_currency.join;

import lombok.Data;



@Data
public class NewSalesVo {
    private String userId, localName, paymentName;
    private int unitPrice;
}
