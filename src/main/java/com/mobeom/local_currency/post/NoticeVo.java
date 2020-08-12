package com.mobeom.local_currency.post;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NoticeVo {
    private String category;
    private String postTitle;
    private LocalDate regDate;
    private String contents;
    private boolean noticeYn;
    private Long userId;
}