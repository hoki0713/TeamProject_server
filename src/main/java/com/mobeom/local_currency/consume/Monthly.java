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
@Table(name="monthly")
public class Monthly {



        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="monthly_id", nullable = false)
        private Long consumeId;

        @Column(name="period", nullable = false)
        private String period;

        @Column(name="industry_name", nullable = false)
        private String industryName;

        @Column(name="amount", nullable = false)
        private long amount;

    }


