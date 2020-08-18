package com.mobeom.local_currency.join;

import com.mobeom.local_currency.store.Store;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class IndustryStore extends Store {
    private String storeName, mainCode, industryType, imgUrl, localName, address;
            double starRanking;
            int storeTypeCode;


}
