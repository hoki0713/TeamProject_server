package com.mobeom.local_currency.favorites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mobeom.local_currency.store.Store;
import com.mobeom.local_currency.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter @Setter @ToString
@Entity
@Table(name = "favorites")
public class Favorites {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}
