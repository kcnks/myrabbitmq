package com.javatiaocao.myblog.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javatiaocao.myblog.mapper.ArticleMapper;
import com.javatiaocao.myblog.mapper.FeedbackMapper;
import com.javatiaocao.myblog.model.Feedback;
import com.javatiaocao.myblog.service.FeedbackService;
import com.javatiaocao.myblog.utils.DataMap;
import com.tiaozaowang.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    //查用户信息
    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    FeedbackMapper feedbackMapper;

    @Override
    public DataMap getAllFeedback(int rows, int pageNum) {

        PageHelper.startPage(pageNum, rows);

        List<Feedback> allFeedback = feedbackMapper.getAllFeedback();
        PageInfo<Feedback> pageInfo = new PageInfo<>(allFeedback);

        JSONObject returnObject = new JSONObject();
        JSONArray feedbackArr = new JSONArray();

        JSONObject map;
        for (Feedback feedback : allFeedback) {
            map = new JSONObject();
            map.put("feedbackContent", feedback.getFeedbackContent());
            map.put("feedbackDate", feedback.getFeedbackDate());
            map.put("person", articleMapper.getUsernameById(feedback.getPersonId()));

            if (feedback.getContactInfo() == null) {
                map.put("contactInfo", StringUtil.BLANK);    //为空的话，给前端返回的是StringUtil.BLANK，不是null
            } else {
                map.put("contactInfo", feedback.getContactInfo());
            }
            feedbackArr.add(map);
        }

        returnObject.put("result", feedbackArr);

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

    @Override
    public DataMap submitFeedback(Feedback feedback) {

        feedbackMapper.insertFeedback(feedback);

        return DataMap.success();
    }
}
