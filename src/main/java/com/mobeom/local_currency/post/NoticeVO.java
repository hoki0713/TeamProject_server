package com.mobeom.local_currency.post;

import lombok.Data;
import org.joda.time.DateTime;

import java.time.LocalDate;

@Data
public class NoticeVO {
    private Long postId;
    private String category;
    private String postTitle;
    private LocalDate regDate;
    private int readCount;
    private String contents;

    public NoticeVO() {}

    public NoticeVO(Long postId, String category, String postTitle, LocalDate regDate, int readCount,String contents) {
        this.postId = postId;
        this.category = category;
        this.postTitle = postTitle;
        this.regDate = regDate;
        this.readCount = readCount;
        this.contents = contents;
    }


}
