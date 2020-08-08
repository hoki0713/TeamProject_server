package com.mobeom.local_currency.rating;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mobeom.local_currency.store.Store;
import com.mobeom.local_currency.user.User;
import com.mobeom.local_currency.voucher.LocalCurrencyVoucher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Controller;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name="rating")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id", nullable = false) private Long recommendId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "id")
    private Store store;

    @Column(name="rating", nullable = false)
    private float rating;


}
