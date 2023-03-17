package com.javatiaocao.myblog.model;

import lombok.Data;

@Data
public class Article {

    private int id;

    //文章ID
    private long articleId;

    //作者
    private String author;

    //原创作者
    private String originalAuthor;

    //文章标题
    private String articleTitle;

    //文章内容
    private String articleContent;

    //文章标签
    private String articleTags;

    //文章类型
    private String articleType;

    //文章分类
    private String articleCategories;

    //发布博客日期
    private String publishDate;

    //更新博客日期
    private String updateDate;

    //转载网址
    private String articleUrl;

    //文章摘要
    private String articleTabloid;

    //点赞数
    private int likes = 0;

    //最后一片文章的ID
    private long lastArticleId;

    //下一篇文章ID
    private long nextArticleId;


}
