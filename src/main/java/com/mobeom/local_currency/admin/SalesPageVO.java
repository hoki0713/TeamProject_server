package com.mobeom.local_currency.admin;

import com.mobeom.local_currency.join.SalesVoucherUser;
import com.mobeom.local_currency.sales.Sales;
import lombok.Data;

import java.util.List;

@Data
public class SalesPageVO {
    private long totalPages;
    private List<SalesVoucherUser> salesList;
}
