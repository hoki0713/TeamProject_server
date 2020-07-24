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
    @Column(name = "admin_id") private Long id;

    @Column(name = "industry_code", nullable = false) private int industryCode;

    @Column(name = "amount_used", nullable = false) private int amountUsed;

    @Column(name = "city", nullable = false,length = 20) private String city;

    @Column(name = "classify_industry", nullable = false,length = 20) private String storeType;
}
