package com.mobeom.local_currency.favorites;

import lombok.Data;

@Data
public class FavoritesVO {
    private String storeName;
    private String storeAddr;
    private String storePhoneNumber;
    private int storeStarRating;
}
