package com.mobeom.local_currency.admin;

import com.mobeom.local_currency.user.RequestedUsersVO;
import lombok.Data;

import java.util.List;

@Data
public class UserPageVO {
    private long totalPages;
    private long totalUsers;
    private List<RequestedUsersVO> users;
}
