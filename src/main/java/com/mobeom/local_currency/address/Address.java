package com.mobeom.local_currency.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mobeom.local_currency.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @ToString @NoArgsConstructor
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "default_addr", nullable = false)
    private String defaultAddr;

    @Column(name = "optional_addr", nullable = false)
    private String optionalAddr;

    @ManyToOne
    @JoinColumn(referencedColumnName = "")
    private User user;

}
