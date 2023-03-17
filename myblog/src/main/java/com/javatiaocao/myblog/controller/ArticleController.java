package com.javatiaocao.myblog.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.service.ArticleService;
import com.javatiaocao.myblog.utils.*;
import com.tiaozaowang.constant.CodeType;
import com.tiaozaowang.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
public class ArticleController {

    @Autowired
    ArticleService articleService;


    //发布文章   用另一种方法尝试获取登录用户名：@AuthenticationPrincipal Principal principal
    @PostMapping(value = "/publishArticle")
    public String publishArticle(Article article, @RequestParam("articleGrade") String articleGrade, HttpServletRequest request) {

        try {

            //获取文章作者
            Principal principal = request.getUserPrincipal();
            String author = principal.getName();


            //获取html代码中中生成的文章摘要  ----------   ？？？？？？？
            BuildArticleTabloidUtil buildArticleTabloidUtil = new BuildArticleTabloidUtil();
            String newArticleHtmlContent = buildArticleTabloidUtil.buildArticleTabloid(request.getParameter("articleHtmlContent"));
            //赋值给article文章摘要
            article.setArticleTabloid(newArticleHtmlContent);


            //处理前端获取过来的标签  如特殊字符，空格    ---     ？？？？？？ 为什么要处理，直接接收了写进去不就行了
            String[] articleTags = request.getParameterValues("articleTagsValue");
            //新的数组
            String[] newArticleTags = new String[articleTags.length + 1];

            for (int i = 0; i < articleTags.length; i++) {
                newArticleTags[i] = articleTags[i].replaceAll("<br>", StringUtil.BLANK).replaceAll("$nbsp", StringUtil.BLANK).replaceAll("\\s", StringUtil.BLANK).trim();
            }
            newArticleTags[articleTags.length] = article.getArticleType();

            articleService.insertTags(newArticleTags, Integer.parseInt(articleGrade));   //articleGrade是前端传的字符型的15,17,18,20。需要转换成真正的int


            //插入对应的标签实体类  ？？？  一片文章对应多个标签，Article中为什么不是List集合类型呢？？？？？---数组转字符串返回进行封装

            //通过文章id来判断是修改文章还是创建文章----有id事修改，无id是发布   由其他关键字来建立查询获取id
            String id = request.getParameter("id");
//            articleService.findIsExistById(id);
            //有前端传过来的id判断
            if (!StringUtil.BLANK.equals(id) && id != null) {
                //修改文章
                TimeUtil timeUtil = new TimeUtil();
                String updateDate = timeUtil.getFormatDateForThree();
                long articleId = timeUtil.getLongTime();
                article.setArticleId(articleId);
                article.setUpdateDate(updateDate);
                article.setId(Integer.parseInt(id));
                article.setArticleTags(StringAndArray.arrayToString(newArticleTags));
                article.setAuthor(author);

                DataMap data = articleService.updateArticleById(article);
                return JsonResult.build(data).toJSON();

            }

            //否则就新增文章
            TimeUtil timeUtil = new TimeUtil();
            String formatDateForThree = timeUtil.getFormatDateForThree();
            long articleId = timeUtil.getLongTime();
            article.setArticleId(articleId);
            article.setPublishDate(formatDateForThree);
            article.setUpdateDate(formatDateForThree);
            article.setAuthor(author);   //有问题还未解决--用request.getUserPrincipal()获取principal

            String stringTags = StringAndArray.arrayToString(newArticleTags);
            article.setArticleTags(stringTags);                  //有问题还未解决---已解决
            DataMap data = articleService.insertArticle(article);


            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("CategoriesController_updateCategory Exception", e);

        }

        return JsonResult.fail(com.tiaozaowang.constant.CodeType.SERVER_EXCEPTION).toJSON();


    }


    //文章管理----获取所有文章信息
    @PostMapping("/getArticleManagement")
    public String getArticleManagement(@RequestParam("rows") int rows, @RequestParam("pageNum") int pageNum) {

        try {

            DataMap data = articleService.getArticleManagement(rows, pageNum);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("ArticleController_getArticleManagement Exception", e);

        }

        return JsonResult.fail(com.tiaozaowang.constant.CodeType.SERVER_EXCEPTION).toJSON();
    }


    //删除文章
    @GetMapping("/deleteArticle")
    public String deleteArticle(String id) {

        try {

            if (StringUtil.BLANK.equals(id) || id == null) {
                return JsonResult.fail(CodeType.DELETE_ARTICLE_FAIL).toJSON();
            }
            DataMap data = articleService.deleteArticle(id);
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("ArticleController_getArticleManagement Exception", e);

        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


    //获取草稿文章或者修改文章
    @GetMapping("/getDraftArticle")
    public String getDraftArticle(HttpServletRequest request) {

        try {

            String id = (String) request.getSession().getAttribute("id");

            //判断文章是否修改
            if (id != null) {

                Article article = articleService.getArticleById(id);
                int lastIndexOf = article.getArticleTags().lastIndexOf(",");
                if (lastIndexOf != -1) {
                    //标签有多个
                    String[] tagArr = StringAndArray.stringToArray(article.getArticleTags().substring(0, lastIndexOf));
                    DataMap data = articleService.getDraftArticle(article, tagArr, articleService.getTagsSizeByName(tagArr[0]));
                    return JsonResult.build(data).toJSON();
                } else {
                    //标签只有一个
                    String[] tagArr = StringAndArray.stringToArray(article.getArticleTags());
                    DataMap data = articleService.getDraftArticle(article, tagArr, articleService.getTagsSizeByName(tagArr[0]));
                    return JsonResult.build(data).toJSON();
                }
            }

            //判断写文章是否登录超时
            if (request.getSession().getAttribute("article") != null) {
                Article article = (Article) request.getSession().getAttribute("article");
                String[] tags = (String[]) request.getSession().getAttribute("articleTags");
                String articleGrade = (String) request.getSession().getAttribute("articleGrade");
                if (!StringUtil.BLANK.equals(articleGrade) && articleGrade != null) {
                    DataMap data = articleService.getDraftArticle(article, tags, Integer.parseInt(articleGrade));

                    request.getSession().removeAttribute("article");
                    request.getSession().removeAttribute("articleTags");
                    request.getSession().removeAttribute("articleGrade");
                    return JsonResult.build(data).toJSON();
                }


            }
            return JsonResult.fail().toJSON();
        } catch (Exception e) {
            log.error("ArticleController_getDraftArticle Exception", e);

        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


    //获取个人信息
    @PostMapping("/getUserPersonalInfo")
    public String getUserPersonalInfo(HttpServletRequest request) {

        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (!Objects.isNull(userPrincipal)) {
                String phone = userPrincipal.getName();
                DataMap data = articleService.getUserPersonalInfo(phone);
                return JsonResult.build(data).toJSON();
            }
        } catch (Exception e) {
            log.error("ArticleController_getUserPersonalInfo Exception", e);

        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


    //验证当前登录用户是否有编辑获取发表权限
    @GetMapping("/canYouWrite")
    public String canYouWrite(HttpServletRequest request) {

        try {
            Principal userPrincipal = request.getUserPrincipal();
            if (!Objects.isNull(userPrincipal)) {
                String phone = userPrincipal.getName();

                boolean bool = articleService.userIsExist(phone);
                if (bool) {
                    return JsonResult.success().toJSON();

                }

            }

            return JsonResult.fail().toJSON();
        } catch (Exception e) {
            log.error("ArticleController_getUserPersonalInfo Exception", e);

        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


    //发送反馈信息
    @PostMapping("/getArticleByArticleId")
    public String getArticleByArticleId(@RequestParam("articleId") String articleId) {

        try {

            DataMap data = articleService.getArticleByArticleId(Integer.parseInt(articleId));

            return JsonResult.build(data).toJSON();

        } catch (Exception e) {
            log.error("ArticleController_getUserPersonalInfo Exception", e);

        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


    //完善个人资料
    @PostMapping("/savePersonalDate")
    public String savePersonalDate(@RequestParam("trueName") String trueName, @RequestParam("birthday") String birthday, @RequestParam("email") String email, @RequestParam("personalBrief") String personalBrief, HttpServletRequest request) {

        try {
            Principal userPrincipal = request.getUserPrincipal();
            String phone = userPrincipal.getName();
            User user = new User(phone, trueName, birthday, email, personalBrief);

            DataMap data = articleService.savePersonalDate(user);

            return JsonResult.build(data).toJSON();

        } catch (Exception e) {
            log.error("ArticleController_getUserPersonalInfo Exception", e);

        }
        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();
    }


}
