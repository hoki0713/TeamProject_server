package com.mobeom.local_currency.sales;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name="sales")
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_id", nullable = false) private Long salesId;
    @CreationTimestamp
    @Column(name = "sales_date", nullable = false) private LocalDateTime salesDate;
    @Column(name = "unit_price", nullable = false) private int unitPrice;
    @CreationTimestamp
    @Column(name = "use_date", nullable = false) private LocalDateTime useDate;
    @Column(name = "gift_yn", nullable = false) private int giftYn;
    @CreationTimestamp
    @Column(name = "cancel_date", nullable = false) private LocalDateTime cancelDate;
    @Column(name = "currency_state", nullable = false) private int currencyState;
    @Column(name = "payment_code", nullable = false) private int paymentCode;
    @Column(name = "payment_type_code", nullable = false) private int paymentTypeCode;
    @Column(name = "payment_name", nullable = false) private String paymentName;




}
