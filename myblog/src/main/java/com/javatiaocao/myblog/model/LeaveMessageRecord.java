package com.javatiaocao.myblog.model;


import lombok.Data;

@Data
public class LeaveMessageRecord {

    private int id;
    private String pageName;
    private int pId;
    private int answererId;
    private int respondentId;
    private String leaveMessageDate;
    private int likes;
    private String leaveMessageContent;
    private int isRead;

}
