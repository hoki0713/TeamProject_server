package com.mobeom.local_currency.currency;

import javax.persistence.*;

@Entity
@Table(name = "currency")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_id")
    private Long currencyId;

    @Column(name = "use_local", nullable = false)
    private String useLocal;

    /*@JsonIgnore
    @OneToOne(mappedBy = "voucher", cascade = CascadeType.ALL)
    private Voucher vouchers;
*/
}
