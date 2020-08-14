package com.mobeom.local_currency.store;

import lombok.Data;

@Data
public class StoresVO {
    private String storeName;
    private String mainCode;
    private int storeTypeCode;
    private String storeType;
    private String localName;
    private String address;
    private String storePhone;
    private double latitude;
    private double longitude;
    private String imgUrl;
}
