package com.mobeom.local_currency.consume;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@Table(name="consume")
public class Consume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="consume_id", nullable = false)
    private Long consumeId;

    @Column(name="month", nullable = false)
    private String month;

    @Column(name="city", nullable = false)
    private String city;

    @Column(name="gender_code", nullable = false)
    private String genderCode;

    @Column(name="industry_name", nullable = false)
    private String industryName;

    @Column(name="amount", nullable = false)
    private String amount;

}
