package com.mobeom.local_currency.join;

import com.mobeom.local_currency.store.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class IndustryStore extends Store {
    private String storeName, mainCode, industryType, imgUrl, localName, address;
            double starRanking;
}
