package com.mobeom.local_currency.admin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity @Getter @Setter @ToString @NoArgsConstructor
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "industry_code") private Long id;

    @Column(name = "amount_used") private int amountUsed;

    @Column(name = "city") private String city;

    @Column(name = "store_type") private String storeType;
}
