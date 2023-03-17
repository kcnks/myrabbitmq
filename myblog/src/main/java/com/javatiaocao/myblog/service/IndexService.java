package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.utils.DataMap;

public interface IndexService {

    DataMap getMyArticles(int rows, int pageNum);

    DataMap getTagArticle(String tag, int rows, int pageNum);

    DataMap getSiteInfo();

    DataMap findTagsCloud();


}
