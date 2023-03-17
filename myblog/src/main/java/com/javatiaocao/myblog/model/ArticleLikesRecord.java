package com.javatiaocao.myblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleLikesRecord {

    private long id;
    private long articleId;
    private int likerId;
    private String likeDate;
    private int isRead;

    public ArticleLikesRecord(long articleId, int likerId, String likeDate, int isRead) {
        this.articleId = articleId;
        this.likerId = likerId;
        this.likeDate = likeDate;
        this.isRead = isRead;
    }
}
