package com.mobeom.local_currency.join;

import com.mobeom.local_currency.industry.Industry;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class IndustryStore {
    private String storeName, mainCode, industry_type, imgUrl;
}
