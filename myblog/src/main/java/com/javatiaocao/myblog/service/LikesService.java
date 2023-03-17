package com.javatiaocao.myblog.service;

//点赞实现接口

import com.javatiaocao.myblog.model.ArticleLikesRecord;
import com.javatiaocao.myblog.utils.DataMap;

public interface LikesService {
    DataMap getArticleThumbsUp(int rows, int pageNum);

    DataMap readAllThumbsUp();

    boolean isLike(long articleId, int userId);

    void insertArticleLikesRecord(ArticleLikesRecord articleLikesRecord);
}
