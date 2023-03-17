package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.model.ArticleLikesRecord;
import com.javatiaocao.myblog.service.ArticleService;
import com.javatiaocao.myblog.service.Impl.RedisServiceImpl;
import com.javatiaocao.myblog.service.LikesService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import com.javatiaocao.myblog.utils.TimeUtil;
import com.tiaozaowang.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import com.tiaozaowang.constant.CodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@Slf4j
public class LikesController {

    @Autowired
    LikesService likesService;
    @Autowired
    ArticleService articleService;
    @Autowired
    RedisServiceImpl redisServiceImpl;

    //点赞管理列表接口
    @PostMapping("/getArticleThumbsUp")
    public String getArticleThumbsUp(@RequestParam(value = "rows") int rows, @RequestParam(value = "pageNum") int pageNum) {

        try {

            DataMap data = likesService.getArticleThumbsUp(rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("ArticleController_getArticleManagement Exception", e);

        }

        return JsonResult.fail(com.tiaozaowang.constant.CodeType.SERVER_EXCEPTION).toJSON();

    }


    //全部标记为已读
    @GetMapping("/readAllThumbsUp")
    public String readAllThumbsUp() {

        try {

            DataMap data = likesService.readAllThumbsUp();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("ArticleController_getArticleManagement Exception", e);

        }

        return JsonResult.fail(com.tiaozaowang.constant.CodeType.SERVER_EXCEPTION).toJSON();

    }


    //新增文章点赞
    @GetMapping("/addArticleLike")
    public String addArticleLike(@RequestParam("articleId") String articleId, @AuthenticationPrincipal Principal principal, HttpServletRequest request) {

        try {
            Principal userPrincipal = request.getUserPrincipal();
            String phone = userPrincipal.getName();
            int userId = articleService.getUseridByPhone(phone);

            if (likesService.isLike(Long.valueOf(articleId), userId)) {
                return JsonResult.fail(CodeType.ARTICLE_HAS_THUMBS_UP).toJSON();
            }
            DataMap data = articleService.updateArticleLikes(articleId);
            ArticleLikesRecord articleLikesRecord = new ArticleLikesRecord(Long.valueOf(articleId), userId, new TimeUtil().getFormatDateForSix(), 1);
            likesService.insertArticleLikesRecord(articleLikesRecord);
            //更新redis中的数据
            redisServiceImpl.readThumbsUpInRedis(StringUtil.ARTICLE_THUMBS_UP, 1);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("ArticleController_getArticleManagement Exception", e);

        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();

    }
}
