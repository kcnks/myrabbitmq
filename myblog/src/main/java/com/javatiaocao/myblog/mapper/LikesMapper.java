package com.javatiaocao.myblog.mapper;


import com.javatiaocao.myblog.model.ArticleLikesRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LikesMapper {


    void readAllThumbsUp();

    List<ArticleLikesRecord> getArticleThumbsUp();

    Integer getMsgIsNotReadNum();

    ArticleLikesRecord isLike(long articleId, int userId);

    void insertArticleLikesRecord(ArticleLikesRecord articleLikesRecord);
}
