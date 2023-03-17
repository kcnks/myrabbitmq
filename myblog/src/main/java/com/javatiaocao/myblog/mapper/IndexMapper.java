package com.javatiaocao.myblog.mapper;


import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.model.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IndexMapper {

    List<Article> getMyArticles();

    int getArticlesNum();

    int getTagsNum();

    int getCommentsNum();

    int getLeaveWordsNum();

    List<Tag> getAllTagObject();

    int getNotReadCommentNum();

    int getNotReadLeaveMessageNum();
}
