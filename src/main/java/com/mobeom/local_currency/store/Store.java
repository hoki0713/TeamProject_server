package com.mobeom.local_currency.store;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity @Getter @Setter @ToString @NoArgsConstructor
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_no") private Long id;

    @Column(name = "store_name",nullable = false) private String storeName;

    @Column(name = "store_type") private String storeType;

    @Column(name = "road_adress") private String address;

    @Column(name = "store_phone") private String storePhone;

    @Column(name = "latitude") private int latitude;

    @Column(name = "longitude") private int longitude;

    @Column(name = "star_ranking") private int starRanking;

}
