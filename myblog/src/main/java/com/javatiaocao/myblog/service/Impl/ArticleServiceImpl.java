package com.javatiaocao.myblog.service.Impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javatiaocao.myblog.mapper.ArticleMapper;
import com.javatiaocao.myblog.mapper.VisitorMapper;
import com.javatiaocao.myblog.model.*;
import com.javatiaocao.myblog.service.ArticleService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.StringAndArray;
import com.tiaozaowang.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import com.tiaozaowang.constant.CodeType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    VisitorMapper visitorMapper;


    //插入发布文章的所有标签
    @Override
    public void insertTags(String[] newArticleTags, int tagSize) {

        for (int i = 0; i < newArticleTags.length; i++) {   //用增强for也可以

            //插入前要判断原来是否有tagName,防止重复
            int id = articleMapper.tagNameIsExist(newArticleTags[i]);
            if (id == 0) {
                Tag tag = new Tag(newArticleTags[i], tagSize);
                articleMapper.saveTags(tag);

            }

        }

    }


    //发布文章
    @Override
    public DataMap insertArticle(Article article) {

        if (StringUtil.BLANK.equals(article.getOriginalAuthor())) {
            article.setOriginalAuthor(article.getAuthor());
        }
        if (StringUtil.BLANK.equals(article.getArticleUrl())) {
            article.setArticleUrl("/article/" + article.getArticleId());
        }

        articleMapper.insertArticle(article);

        //传递数据响应给前端
        HashMap<String, Object> dataMap = new HashMap<>(4);
        dataMap.put("articleTitle", article.getArticleTitle());
        dataMap.put("updateDate", article.getUpdateDate());
        dataMap.put("articleUrl", "/article/" + article.getArticleUrl());
        dataMap.put("author", article.getAuthor());
        return DataMap.success().setData(dataMap);  //有没有取决于前段需不需要有返回值，和调用Mapper的方法有关系也没有关系
    }


    //分页查询文章管理
    @Override
    public DataMap getArticleManagement(int rows, int pageNum) {


        //开启分页功能
        PageHelper.startPage(pageNum, rows);


        JSONObject json222 = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        List<Article> articleList = articleMapper.getArticleManagement();

        PageInfo<Article> pageInfo = new PageInfo<Article>(articleList);

        for (Article article : articleList) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", article.getId());
            jsonObject.put("articleId", article.getArticleId());
            jsonObject.put("articleTitle", article.getArticleTitle());
            jsonObject.put("publishDate", article.getUpdateDate());
            jsonObject.put("articleCategories", article.getArticleCategories());

            String pageName = "article/" + article.getArticleId();
            jsonObject.put("visitorNum", getvisitorNum(pageName));  //文章浏览量--动态


            jsonArray.add(jsonObject);

        }

        json222.put("result", jsonArray);    //为什么用result???  因为前端就是用的result来取值得，要与前端保持一致

        JSONObject pageJson = new JSONObject();
        pageJson.put("pageSize", pageInfo.getPageSize());
        pageJson.put("pageNum", pageInfo.getPageNum());
        pageJson.put("pages", pageInfo.getPages());
        pageJson.put("totle", pageInfo.getTotal());
        //这两个也写上，后面要用到
        pageJson.put("isFirstPage", pageInfo.isIsFirstPage());
        pageJson.put("isLastPage", pageInfo.isIsLastPage());

        json222.put("pageInfo", pageJson);
        return DataMap.success().setData(json222);
    }


    private long getvisitorNum(String pageName) {

        Visitor visitor = visitorMapper.getVisitorNumBypageName(pageName);
        if (visitor != null) {
            return visitor.getVisitorNum();
        }
        return 0l;
    }


    //删除文章
    @Override
    @Transactional
    public DataMap deleteArticle(String id) {

        //删除时还要删除文章相关联的其他表的数据信息

        //1.根据id查询文章信息
        Article article = articleMapper.findArticleById(id);
        //逻辑删除，实际数据并没有删除，破坏查询条件
        //物理刪除，直接在数据库中删除数据
        articleMapper.updateLastNextId("lastArticleId", article.getLastArticleId(), article.getNextArticleId());
        articleMapper.updateLastNextId("nextArticleId", article.getLastArticleId(), article.getNextArticleId());
        //删除文章
        articleMapper.deleteArticle(id);

        //文章对应的点赞，评论，喜欢的记录进行删除----这些接口还没写，先留着
        //TODO


        return DataMap.success();   // 不用具体返回值

    }


    @Override
    public Article getArticleById(String id) {
        return articleMapper.getArticleById(id);
    }


    @Override
    public Integer getTagsSizeByName(String tagName) {
        return articleMapper.getTagsSizeByName(tagName);
    }


    //将要修改的article查询出来并返回给修改页面（修改页面和发布页面一样）
    @Override
    public DataMap getDraftArticle(Article article, String[] tagArr, Integer tagsSizeByName) {

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", article.getId());
        hashMap.put("articleTitle", article.getArticleTitle());
        hashMap.put("articleContent", article.getArticleContent());
        hashMap.put("articleType", article.getArticleType());
        hashMap.put("articleGrade", article.getId());
        hashMap.put("originalAuthor", article.getOriginalAuthor());
        hashMap.put("articleTags", StringAndArray.stringToArray(article.getArticleTags()));
        hashMap.put("articleCategories", article.getArticleCategories());
        return DataMap.success().setData(hashMap);
    }


    //获取个人信息
    @Override
    public DataMap getUserPersonalInfo(String phone) {

        User user = articleMapper.getUserPersonalInfo(phone);
        return DataMap.success().setData(user);
    }

    @Override
    public boolean userIsExist(String phone) {
        return articleMapper.userIsExist(phone);
    }


    //更新文章
    @Override
    public DataMap updateArticleById(Article article) {

        Article oldArticle = articleMapper.querryArticleById(article.getId());

        article.setPublishDate(oldArticle.getPublishDate());
        if (StringUtil.BLANK.equals(article.getArticleUrl())) {
            //说明类型是原创 ---  原创的Url是写死的
            article.setArticleUrl("/article/" + article.getArticleId());
        }
        if ("原创".equals(article.getArticleType())) {
            article.setOriginalAuthor(article.getAuthor());
        }
        article.setLikes(oldArticle.getLikes());

        articleMapper.updateArticle(article);

        //修改完成后，还要修改文章管理处的查询
        HashMap<String, Object> dataMap = new HashMap<>();

        dataMap.put("articleTitle", article.getArticleTitle());
        dataMap.put("updateDate", article.getUpdateDate());
        dataMap.put("author", article.getAuthor());
        dataMap.put("articleUrl", article.getArticleUrl());

        return DataMap.success().setData(dataMap);
    }

    @Override
    public Map<String, String> findArticleByArticleId(long articleId) {

        Article article = articleMapper.findArticleByArticleId(articleId);
        HashMap<String, String> articleMap = new HashMap<>();

        if (article != null) {
            articleMap.put("articleTitle", article.getArticleTitle());
            articleMap.put("articleTabloid", article.getArticleTabloid());
        }

        return articleMap;
    }

    @Override
    public DataMap getArticleByArticleId(int articleId) {

        Article article = articleMapper.getArticleByArticleId(articleId);
        visitorMapper.insertVisitor("article/" + articleId);
        return DataMap.success().setData(article);
    }

    @Override
    public int getUseridByPhone(String phone) {
        return articleMapper.getUseridByPhone(phone);
    }

    @Override
    public DataMap updateArticleLikes(String articleId) {
        articleMapper.updateArticleLikes(articleId);
        int likes = articleMapper.queryLikesByArticleId(articleId);
        return DataMap.success().setData(likes);
    }

    @Override
    public DataMap savePersonalDate(User user) {

        articleMapper.savePersonalDate(user);
        User dataUser = articleMapper.getUserByPhone(user.getPhone());
        return DataMap.success().setData(dataUser);
    }
}
