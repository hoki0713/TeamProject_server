package com.mobeom.local_currency.user;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RequestedUsersVO {
    private Long id;
    private String userId;
    private String name;
    private LocalDate birthDate;
    private String gender;
    private String email;
    private LocalDate joinDate;
    private String defaultAddr;
    private String optionalAddr;
}
