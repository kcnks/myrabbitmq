package com.javatiaocao.myblog.model;


import lombok.Data;

@Data
public class PrivateWord {


    private int id;
    private String privateWord;
    private String publisherId;
    private String replierId;
    private String replyContent;
    private String publisherDate;


}
