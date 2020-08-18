package com.mobeom.local_currency.join;

import com.mobeom.local_currency.rating.Rating;
import com.mobeom.local_currency.reportList.ReportList;
import com.mobeom.local_currency.store.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReportListStore extends ReportList {
    private String storeName,mainCode,address,storePhone,storeType;
    private Long id;
    private int starRating;
    private int reportedCount;


}
