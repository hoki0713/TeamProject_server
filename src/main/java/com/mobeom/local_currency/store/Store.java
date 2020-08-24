package com.mobeom.local_currency.store;

import com.amazonaws.services.codebuild.model.Report;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mobeom.local_currency.favorites.Favorites;
import com.mobeom.local_currency.post.Post;
import com.mobeom.local_currency.rating.Rating;
import com.mobeom.local_currency.recommend.Industry;
import com.mobeom.local_currency.reportList.ReportList;
import com.mobeom.local_currency.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity @Getter @Setter @ToString
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") private Long id;

    @Column(name = "store_name",nullable = false) private String storeName;

    @Column(name = "main_code",nullable = false) private String mainCode;

    @Column(name = "store_type", nullable = false) private String storeType;

    @Column(name = "store_type_code",nullable = false) private int storeTypeCode;

    @Column(name = "local_name", nullable = false) private String localName;

    @Column(name = "road_address", nullable = false) private String address;

    @Column(name = "store_phone", nullable = false) private String storePhone;

    @Column(name = "latitude",nullable = false) private double  latitude;

    @Column(name = "longitude", nullable = false) private double longitude;

    @Column(name = "search_result_count",nullable = false) private int searchResultCount;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Rating> ratingList;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<Favorites> favoritesList;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL)
    private List<ReportList> reportList;

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
        this.searchResultCount = searchResultCount;

    }
}
