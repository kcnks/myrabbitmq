package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.model.Tag;
import com.javatiaocao.myblog.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ArticleMapper {


    void saveTags(Tag tag);

    int tagNameIsExist(String newArticleTag);

    void insertArticle(Article article);

    List<Article> getArticleManagement();


    void deleteArticle(String id);

    Article findArticleById(String id);

    void updateLastNextId(@Param("lastOrNextstr") String lastOrNextstr, @Param("updateId") long updateId, @Param("articleId") long articleId);   //??????

    Article getArticleById(String id);

    Integer getTagsSizeByName(String tagName);

    User getUserPersonalInfo(String phone);

    boolean userIsExist(String phone);

    void updateArticle(Article article);

    Article querryArticleById(int id);

    Article findArticleByArticleId(long articleId);

    String getArticleTitleByArticleId(long articleId);

    String getUsernameById(int id);

    Article getArticleByArticleId(int articleId);


    long getAllUser();

    long getArticleNum();

    int getUseridByPhone(String phone);

    void updateArticleLikes(String articleId);

    int queryLikesByArticleId(String articleId);

    void savePersonalDate(User user);

    User getUserByPhone(String phone);

    String getUsernameByAnwserId(int id);


}
