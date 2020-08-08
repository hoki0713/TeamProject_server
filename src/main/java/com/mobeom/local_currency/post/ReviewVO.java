package com.mobeom.local_currency.post;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewVO {
    private Long boardId;
    private Long storeId;
    private LocalDate regDate;
    private String postTitle;
    private String contents;
    private LocalDate modiDate;
    private boolean noticeYn;
    private int starRating;
    private Long userId;
}
