package com.mobeom.local_currency.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "id", nullable = false)
    private String id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @CreationTimestamp
    @Column(name = "birth_date", nullable = false)
    private LocalDateTime birthDate;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "email", nullable = false)
    private String email;

    @CreationTimestamp
    @Column(name = "join_date", nullable = false)
    private LocalDateTime joinDate;

    @CreationTimestamp
    @Column(name = "withdraw_date", nullable = false)
    private LocalDateTime withdrawDate;

    @Column(name = "admin_key", nullable = false, columnDefinition = "boolean default 0")
    private Integer adminKey;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "default_addr", nullable = false)
    private String defaultAddr;

    @Column(name = "optional_addr", nullable = false)
    private String optionalAddr;
}
