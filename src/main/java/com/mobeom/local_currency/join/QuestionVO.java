package com.mobeom.local_currency.join;

import lombok.Data;

import java.time.LocalDate;

@Data
public class QuestionVO {
    private Long userId;
    private String userUniqueId;
    private String userName;
    private String postTitle;
    private String contents;
    private String comment;
    private LocalDate regDate;
    private LocalDate modiDate;
    private boolean noticeYn;
}
