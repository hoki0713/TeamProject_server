package com.mobeom.local_currency.join;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReviewRatingVO {
    private Long storeId;
    private String storeName;
    private LocalDate regDate;
    private String contents;
    private LocalDate modiDate;
    private boolean noticeYn;
    private int starRating;
    private Long userId;
}
