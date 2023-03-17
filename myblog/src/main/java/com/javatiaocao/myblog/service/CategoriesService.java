package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.model.Category;
import com.javatiaocao.myblog.utils.DataMap;

import java.util.List;

public interface CategoriesService {

    boolean categoryNameIsExist(String categoryName);

    DataMap getArticleCategories();

    DataMap updateCategory(String categoryName, int type);

    DataMap findCategoriesNames();
}
