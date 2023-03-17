package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CategoriesMapper {

    List<Category> getArticleCategories();

    Category categoryNameIsExist(String categoryName);

    void saveCategory(String categoryName);

    void deleteCategory(String categoryName);

    List<String> findCategoriesNames();
}
