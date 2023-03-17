package com.javatiaocao.myblog.controller;


import com.javatiaocao.myblog.service.CategoriesService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.tiaozaowang.constant.CodeType;

import java.util.List;

@RestController
@Slf4j
public class CategoriesController {

    @Autowired
    private CategoriesService categoriesService;


    //查询分类列表接口
    @GetMapping("/getArticleCategories")
    public String getArticleCategories() {

        try {

            DataMap data = categoriesService.getArticleCategories();
            return JsonResult.build(data).toJSON();         //data??????????

        } catch (Exception e) {
            log.error("CategoriesController_getArticleCategories Exception", e);

        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();


    }


    //更新分类----创建或删除分类
    @PostMapping("/updateCategory")
    public String updateCategory(@RequestParam("categoryName") String categoryName, @RequestParam("type") int type) {

        try {


            DataMap data = categoriesService.updateCategory(categoryName, type);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("CategoriesController_updateCategory Exception", e);

        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();

    }

    //查询分类列表接口
    @GetMapping("/findCategoriesName")
    public String findCategoriesName() {

        try {

            DataMap data = categoriesService.findCategoriesNames();
            return JsonResult.build(data).toJSON();         //data??????????

        } catch (Exception e) {
            log.error("CategoriesController_findCategoriesName Exception", e);

        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();


    }


}
