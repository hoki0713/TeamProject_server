package com.mobeom.local_currency.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mobeom.local_currency.sales.Sales;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id"})})
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "join_date", nullable = false, columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP" )
    private LocalDate joinDate;

    @Column(name = "withdraw_date")
    private String withdrawDate;

    @Column(name = "admin_key", nullable = false, columnDefinition = "boolean default 0")
    private Integer adminKey;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "default_addr", nullable = false)
    private String defaultAddr;

    @Column(name = "optional_addr")
    private String optionalAddr;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Sales> salesList;
}
