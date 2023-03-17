package com.javatiaocao.myblog.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javatiaocao.myblog.mapper.IndexMapper;
import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.model.Tag;
import com.javatiaocao.myblog.service.IndexService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.StringAndArray;
import com.tiaozaowang.constant.CodeType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class IndexServiceImpl implements IndexService {

    @Autowired
    IndexMapper indexMapper;


    @Override
    public DataMap getMyArticles(int rows, int pageNum) {
        //开启分页功能
        PageHelper.startPage(pageNum, rows);


        //JSONObject json222 = new JSONObject();
        JSONArray data = new JSONArray();

        List<Article> articleList = indexMapper.getMyArticles();

        PageInfo<Article> pageInfo = new PageInfo<>(articleList);

        for (Article article : articleList) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", article.getId());
            jsonObject.put("articleId", article.getArticleId());
            jsonObject.put("articleTitle", article.getArticleTitle());
            jsonObject.put("articleType", article.getArticleType());
            jsonObject.put("publishDate", article.getUpdateDate());
            jsonObject.put("articleCategories", article.getArticleCategories());
            jsonObject.put("originalAuthor", article.getOriginalAuthor());
            jsonObject.put("articleTabloid", article.getArticleTabloid());
            jsonObject.put("thisArticleUrl", article.getArticleUrl());
            jsonObject.put("articleTags", StringAndArray.stringToArray(article.getArticleTags()));
            jsonObject.put("likes", article.getLikes());

            data.add(jsonObject);

        }

        JSONObject pageJson = new JSONObject();
        pageJson.put("pageSize", pageInfo.getPageSize());
        pageJson.put("pageNum", pageInfo.getPageNum());
        pageJson.put("pages", pageInfo.getPages());
        pageJson.put("total", pageInfo.getTotal());
        //这两个也写上，后面要用到
        pageJson.put("isFirstPage", pageInfo.isIsFirstPage());
        pageJson.put("isLastPage", pageInfo.isIsLastPage());

        //json222.put("pageInfo",pageJson);


        data.add(pageJson);    //为什么用data???  因为前端就是用的data来取值得，要与前端保持一致


        return DataMap.success().setData(data);
    }


    //获取标签文章
    @Override
    public DataMap getTagArticle(String tag, int rows, int pageNum) {
        //开启分页功能
        PageHelper.startPage(pageNum, rows);


        //JSONObject json222 = new JSONObject();
        JSONArray data = new JSONArray();

        List<Article> articleList = indexMapper.getMyArticles();

        PageInfo<Article> pageInfo = new PageInfo<>(articleList);

        for (Article article : articleList) {

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", article.getId());
            jsonObject.put("articleId", article.getArticleId());
            jsonObject.put("articleTitle", article.getArticleTitle());
            jsonObject.put("articleType", article.getArticleType());
            jsonObject.put("publishDate", article.getUpdateDate());
            jsonObject.put("articleCategories", article.getArticleCategories());
            jsonObject.put("originalAuthor", article.getOriginalAuthor());
            jsonObject.put("articleTabloid", article.getArticleTabloid());
            jsonObject.put("thisArticleUrl", article.getArticleUrl());
            jsonObject.put("articleTags", StringAndArray.stringToArray(article.getArticleTags()));
            jsonObject.put("likes", article.getLikes());

            data.add(jsonObject);

        }

        JSONObject pageJson = new JSONObject();
        pageJson.put("pageSize", pageInfo.getPageSize());
        pageJson.put("pageNum", pageInfo.getPageNum());
        pageJson.put("pages", pageInfo.getPages());
        pageJson.put("total", pageInfo.getTotal());
        //这两个也写上，后面要用到
        pageJson.put("isFirstPage", pageInfo.isIsFirstPage());
        pageJson.put("isLastPage", pageInfo.isIsLastPage());

        //json222.put("pageInfo",pageJson);


        data.add(pageJson);    //为什么用data???  因为前端就是用的data来取值得，要与前端保持一致


        return DataMap.success().setData(data);
    }

    @Override
    public DataMap getSiteInfo() {

        HashMap<String, Object> dataMap = new HashMap<>();
        int articlesNum = indexMapper.getArticlesNum();
        int tagsNum = indexMapper.getTagsNum();
        int commentsNum = indexMapper.getCommentsNum();
        int leaveWordsNum = indexMapper.getLeaveWordsNum();

        dataMap.put("articleNum", articlesNum);
        dataMap.put("tagsNum", tagsNum);
        dataMap.put("commentNum", commentsNum);
        dataMap.put("leaveWordNum", leaveWordsNum);


        return DataMap.success().setData(dataMap);
    }

    @Override
    public DataMap findTagsCloud() {

        List<Tag> tags = indexMapper.getAllTagObject();

        JSONObject dataMap = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        JSONObject map;
        for (Tag tag : tags) {
            map = new JSONObject();
            map.put("tagName", tag.getTagName());
            map.put("tagSize", tag.getTagSize());

            jsonArray.add(map);
        }

        dataMap.put("result", jsonArray);
        return DataMap.success(CodeType.FIND_TAGS_CLOUD).setData(dataMap);
    }

//    @Override
//    public DataMap getUserNews() {
//        HashMap<String, Object> dataMap = new HashMap<>();
//
//        JSONObject returnData = new JSONObject();
//
//        int commentNum = indexMapper.getNotReadCommentNum();
//        int leaveMessageNum = indexMapper.getNotReadLeaveMessageNum();
//        int allNewsNum = commentNum+leaveMessageNum;
//
//        dataMap.put("allNewsNum",allNewsNum);
//        dataMap.put("commentNum",commentNum);
//        dataMap.put("leaveMessageNum",leaveMessageNum);
//
//        returnData.put("result",dataMap);
//
//        return DataMap.success().setData(dataMap);
//    }
}
