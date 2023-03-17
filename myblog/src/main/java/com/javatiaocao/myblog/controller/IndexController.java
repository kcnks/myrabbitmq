package com.javatiaocao.myblog.controller;


import com.javatiaocao.myblog.service.Impl.RedisServiceImpl;
import com.javatiaocao.myblog.service.IndexService;
import com.javatiaocao.myblog.service.RedisService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import com.tiaozaowang.constant.CodeType;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Objects;

@RestController
@Slf4j
public class IndexController {

    @Autowired
    IndexService indexService;

    @Autowired
    RedisServiceImpl redisServiceImpl;

    @PostMapping("/myArticles")
    public String getMyArticles(@RequestParam("rows") int rows, @RequestParam("pageNum") int pageNum) {
        try {

            DataMap data = indexService.getMyArticles(rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("ArticleController_getArticleManagement Exception", e);

        }

        return JsonResult.fail(com.tiaozaowang.constant.CodeType.SERVER_EXCEPTION).toJSON();
    }


    @PostMapping("/getTagArticle")
    public String getTagArticle(@RequestParam("tag") String tag, @RequestParam("rows") int rows, @RequestParam("pageNum") int pageNum) {
        try {

            DataMap data = indexService.getTagArticle(tag, rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("ArticleController_getArticleManagement Exception", e);

        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


    @GetMapping("/getSiteInfo")
    public String getSiteInfo() {
        try {

            DataMap data = indexService.getSiteInfo();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("ArticleController_getArticleManagement Exception", e);

        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


    @GetMapping("/findTagsCloud")
    public String findTagsCloud() {
        try {

            DataMap data = indexService.findTagsCloud();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("ArticleController_getArticleManagement Exception", e);

        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }

    @PostMapping("/getUserNews")
    public String getUserNews(HttpServletRequest request) {
        try {

            Principal principal = request.getUserPrincipal();

            if (!Objects.isNull(principal)) {
                String phone = principal.getName();
                DataMap data = redisServiceImpl.getUserNews(phone);
                return JsonResult.build(data).toJSON();
            } else {
                JSONObject json = new JSONObject();
                json.put("result", 0);
                return JsonResult.build(DataMap.success().setData(json)).toJSON();
            }


        } catch (Exception e) {
            log.error("ArticleController_getArticleManagement Exception", e);

        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }
}



