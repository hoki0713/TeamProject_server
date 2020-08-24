package com.mobeom.local_currency.sales;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mobeom.local_currency.user.User;
import com.mobeom.local_currency.voucher.LocalCurrencyVoucher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity @Getter @Setter @NoArgsConstructor
@Table(name="sales")
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_id", nullable = false)
    private Long salesId;

    @Column(name = "sales_date", nullable = false)
    private LocalDate salesDate = LocalDate.now();

    @Column(name = "unit_price", nullable = false)
    private int unitPrice;

    @Column(name = "use_date")
    private LocalDate useDate;

    @Column(name = "gift_yn", nullable = false)
    private boolean giftYn;

    @Column(name = "cancel_date")
    private LocalDate cancelDate;

    @Column(name = "currency_state", nullable = false)
    private String currencyState;

    @Column(name = "payment_name", nullable = false)
    private String paymentName;

    @Column(name = "recipient_email")
    private String recipientEmail;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "local_currency_voucher_id")
    private LocalCurrencyVoucher localCurrencyVoucher;
}
