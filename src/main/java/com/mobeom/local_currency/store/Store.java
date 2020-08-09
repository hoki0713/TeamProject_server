package com.mobeom.local_currency.store;

import lombok.*;

import javax.persistence.*;

@Entity @Getter @Setter @ToString
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") private Long id;

    @Column(name = "store_name",nullable = false) private String storeName;

    @Column(name = "store_type_code",nullable = false) private int storeTypeCode;

    @Column(name = "store_type", nullable = false) private String storeType;

    @Column(name = "local_name", nullable = false) private String localName;

    @Column(name = "road_address", nullable = false) private String address;

    @Column(name = "store_phone", nullable = false) private String storePhone;

    @Column(name = "latitude",nullable = false) private double latitude;

    @Column(name = "longitude", nullable = false) private double longitude;

    @Column(name = "star_ranking",nullable = false) private int starRanking;

    @Column(name = "search_result_count",nullable = false) private int searchResultCount;

    public Store(){}
    @Builder
    public Store(
            String storeName,
            int storeTypeCode,
            String storeType,
            String localName,
            String address,
            String storePhone,
            double latitude,
            double longitude,
            int starRanking,
            int searchResultCount
    ){
        this.storeName = storeName;
        this.storeTypeCode = storeTypeCode;
        this.storeType = storeType;
        this.localName = localName;
        this.address = address;
        this.storePhone = storePhone;
        this.latitude = latitude;
        this.longitude = longitude;
        this.starRanking = starRanking;
        this.searchResultCount = searchResultCount;

    }
}
