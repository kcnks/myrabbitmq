package com.javatiaocao.myblog.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javatiaocao.myblog.mapper.ArticleMapper;
import com.javatiaocao.myblog.mapper.LikesMapper;
import com.javatiaocao.myblog.model.ArticleLikesRecord;
import com.javatiaocao.myblog.service.ArticleService;
import com.javatiaocao.myblog.service.LikesService;
import com.javatiaocao.myblog.utils.DataMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class LikesServiceImpl implements LikesService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    LikesMapper likesMapper;

    @Override
    public DataMap getArticleThumbsUp(int rows, int pageNum) {
        //开启分页插件
        PageHelper.startPage(pageNum, rows);

        List<ArticleLikesRecord> articleThumbsUp = likesMapper.getArticleThumbsUp();
        PageInfo<ArticleLikesRecord> pageInfo = new PageInfo<>(articleThumbsUp);

        JSONObject returnJson = new JSONObject();
        JSONArray dataArr = new JSONArray();

        JSONObject jsonObject;
        for (ArticleLikesRecord articleLikesRecord : articleThumbsUp) {

            jsonObject = new JSONObject();
            jsonObject.put("id", articleLikesRecord.getId());
            jsonObject.put("articleId", articleLikesRecord.getArticleId());
            jsonObject.put("isRead", articleLikesRecord.getIsRead());
            jsonObject.put("likeDate", articleLikesRecord.getLikeDate());
//            jsonObject.put("articleId",articleLikesRecord.getArticleId());
            jsonObject.put("articleTitle", articleMapper.getArticleTitleByArticleId(articleLikesRecord.getArticleId()));
            jsonObject.put("praisePeople", articleMapper.getUsernameById(articleLikesRecord.getLikerId()));

            dataArr.add(jsonObject);
        }
        returnJson.put("result", dataArr);
        returnJson.put("msgIsNotReadNum", likesMapper.getMsgIsNotReadNum());

        JSONObject map = new JSONObject();
        map.put("pageNum", pageInfo.getPageNum());
        map.put("pageSize", pageInfo.getPageSize());
        map.put("pages", pageInfo.getPages());
        map.put("total", pageInfo.getTotal());
        map.put("isFirstPage", pageInfo.isIsFirstPage());
        map.put("isLastPage", pageInfo.isIsLastPage());

        returnJson.put("pageInfo", map);
        return DataMap.success().setData(returnJson);
    }

    @Override
    public DataMap readAllThumbsUp() {


        likesMapper.readAllThumbsUp();

        //TODO 删除redis对应的

        return DataMap.success();
    }

    @Override
    public boolean isLike(long articleId, int userId) {
        ArticleLikesRecord articleLikesRecord = likesMapper.isLike(articleId, userId);
        return articleLikesRecord != null;
    }

    @Override
    public void insertArticleLikesRecord(ArticleLikesRecord articleLikesRecord) {
        likesMapper.insertArticleLikesRecord(articleLikesRecord);
    }
}
