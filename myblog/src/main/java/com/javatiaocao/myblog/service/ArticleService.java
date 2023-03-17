package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.model.Tag;
import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.utils.DataMap;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface ArticleService {

    void insertTags(String[] newArticleTags, int parseInt);

    DataMap insertArticle(Article article);

    DataMap getArticleManagement(int rows, int pageNum);

    DataMap deleteArticle(String id);

    Article getArticleById(String id);

    Integer getTagsSizeByName(String tagName);

    DataMap getDraftArticle(Article article, String[] tagArr, Integer tagsSizeByName);

    DataMap getUserPersonalInfo(String phone);

    boolean userIsExist(String phone);

    DataMap updateArticleById(Article article);

    Map<String, String> findArticleByArticleId(long articleId);

    DataMap getArticleByArticleId(int articleId);

    int getUseridByPhone(String phone);

    DataMap updateArticleLikes(String articleId);

    DataMap savePersonalDate(User user);
}
