package com.mobeom.local_currency.favorites;

import lombok.Data;

@Data
public class FavoritesVO {
    private Long userId;
    private Long storeId;
    private String storeName;
    private String storeAddr;
    private String storePhoneNumber;
}
