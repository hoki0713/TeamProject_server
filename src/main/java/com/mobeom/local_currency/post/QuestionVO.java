package com.mobeom.local_currency.post;

import lombok.Data;

import java.time.LocalDate;

@Data
public class QuestionVO {
    private Long userId;
    private String postTitle;
    private String contents;
    private String comment;
    private LocalDate regDate;
    private LocalDate modiDate;
    private boolean noticeYn;
    private int starRating;
}
