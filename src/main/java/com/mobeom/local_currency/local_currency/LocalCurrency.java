package com.mobeom.local_currency.local_currency;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "local_currency")
public class LocalCurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "local_currency_id")
    private Long localCurrencyId;

    @Column(name = "use_local", nullable = false)
    private String useLocal;

    /*@JsonIgnore
    @OneToMany(mappedBy = "voucher", cascade = CascadeType.ALL)
    private List<Voucher> vouchers;
*/
}
