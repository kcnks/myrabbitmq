package com.javatiaocao.myblog.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.javatiaocao.myblog.mapper.CategoriesMapper;
import com.javatiaocao.myblog.model.Category;
import com.javatiaocao.myblog.service.CategoriesService;
import com.javatiaocao.myblog.utils.DataMap;
import com.tiaozaowang.constant.CodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    private CategoriesMapper categoriesMapper;

    @Override
    public boolean categoryNameIsExist(String categoryName) {

        return categoriesMapper.categoryNameIsExist(categoryName) != null;
    }

    @Override
    public DataMap getArticleCategories() {
        JSONObject json111 = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        List<Category> categoryList = categoriesMapper.getArticleCategories();
        for (Category category : categoryList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", category.getId());
            jsonObject.put("categoryName", category.getCategoryName());


            jsonArray.add(jsonObject);

        }

        json111.put("result", jsonArray);    //为什么用result???  因为前端就是用的result来取值得，要与前端保持一致

        return DataMap.success().setData(json111);
    }

    @Override
    public DataMap updateCategory(String categoryName, int type) {

        Category category = categoriesMapper.categoryNameIsExist(categoryName);
        //int  idBycategoryName = categoriesMapper.categoryNameIsExist(categoryName);

        if (type == 1) {   //type==1,则表示新增分类
            if (category == null) {
                categoriesMapper.saveCategory(categoryName);

                //????????????????????? 因为前端规范的返回值就是这样
                Integer newCategoryId = categoriesMapper.categoryNameIsExist(categoryName).getId();
                return DataMap.success(CodeType.ADD_CATEGORY_SUCCESS).setData(newCategoryId);

            }
        } else {
            if (category != null) {
                //1如果对应分类下有文章，则删除失败


                //2删除
                categoriesMapper.deleteCategory(categoryName);
                return DataMap.success(CodeType.DELETE_CATEGORY_SUCCESS);
            }
        }

        return DataMap.fail(CodeType.CATEGORY_NOT_EXIST);


    }

    @Override
    public DataMap findCategoriesNames() {
        List<String> categoriesNames = categoriesMapper.findCategoriesNames();

        return DataMap.success().setData(categoriesNames);
    }
}
