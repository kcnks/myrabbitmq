package com.javatiaocao.myblog.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javatiaocao.myblog.mapper.ArticleMapper;
import com.javatiaocao.myblog.mapper.CommentMapper;
import com.javatiaocao.myblog.model.CommentRecord;
import com.javatiaocao.myblog.model.LeaveMessageRecord;
import com.javatiaocao.myblog.service.CommentService;
import com.javatiaocao.myblog.utils.DataMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    ArticleMapper articleMapper;

    @Override
    public DataMap newComment(int rows, int pageNum) {

        PageHelper.startPage(pageNum, rows);

        List<CommentRecord> allCommentRecord = commentMapper.getAllComment();
        PageInfo<CommentRecord> pageInfo = new PageInfo<CommentRecord>(allCommentRecord);

        JSONObject returnObject = new JSONObject();
        JSONArray arr = new JSONArray();

        JSONObject map;
        for (CommentRecord commentRecord : allCommentRecord) {
            map = new JSONObject();
            map.put("id", commentRecord.getId());
            map.put("articleId", commentRecord.getArticleId());
            map.put("answerer", articleMapper.getUsernameByAnwserId(commentRecord.getAnswererId()));
            map.put("commentDate", commentRecord.getCommentDate());
            map.put("commentContent", commentRecord.getCommentContent());
            //这个articleTitle我在前端没看到啊？？？？
            map.put("articleTitle", articleMapper.getArticleTitleByArticleId(commentRecord.getArticleId()));

            arr.add(map);
        }


        returnObject.put("result", arr);

        JSONObject pageJson = new JSONObject();
        pageJson.put("pageSize", pageInfo.getPageSize());
        pageJson.put("pageNum", pageInfo.getPageNum());
        pageJson.put("pages", pageInfo.getPages());
        pageJson.put("total", pageInfo.getTotal());

        pageJson.put("isFirstPage", pageInfo.isIsFirstPage());
        pageJson.put("isLastPage", pageInfo.isIsLastPage());

        returnObject.put("pageInfo", pageJson);

        return DataMap.success().setData(returnObject);
    }
}

