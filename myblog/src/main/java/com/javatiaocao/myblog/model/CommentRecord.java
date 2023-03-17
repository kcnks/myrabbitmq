package com.javatiaocao.myblog.model;


import lombok.Data;

@Data
public class CommentRecord {

    private long id;
    private long pId;
    private long articleId;
    private int answererId;
    private int respondentId;
    private String commentDate;
    private int likes;
    private String commentContent;
    private int isRead;

}
