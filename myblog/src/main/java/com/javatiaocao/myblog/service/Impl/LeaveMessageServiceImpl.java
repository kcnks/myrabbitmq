package com.javatiaocao.myblog.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javatiaocao.myblog.mapper.ArticleMapper;
import com.javatiaocao.myblog.mapper.LeaveMessageMapper;
import com.javatiaocao.myblog.model.Feedback;
import com.javatiaocao.myblog.model.LeaveMessageRecord;
import com.javatiaocao.myblog.service.LeaveMessageService;
import com.javatiaocao.myblog.utils.DataMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class LeaveMessageServiceImpl implements LeaveMessageService {

    @Autowired
    LeaveMessageMapper leaveMessageMapper;

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public DataMap getUserLeaveWord(int rows, int pageNum) {

        PageHelper.startPage(pageNum, rows);

        List<LeaveMessageRecord> allLeaveMessageRecord = leaveMessageMapper.getUserLeaveWord();
        PageInfo<LeaveMessageRecord> pageInfo = new PageInfo<>(allLeaveMessageRecord);

        JSONObject returnObject = new JSONObject();
        JSONArray arr = new JSONArray();

        JSONObject map;
        for (LeaveMessageRecord leaveMessageRecord : allLeaveMessageRecord) {
            map = new JSONObject();
            map.put("id", leaveMessageRecord.getId());
            map.put("pageName", leaveMessageRecord.getPageName());
            map.put("pId", leaveMessageRecord.getPId());
            map.put("answerer", articleMapper.getUsernameByAnwserId(leaveMessageRecord.getAnswererId()));
            map.put("leaveMessageDate", leaveMessageRecord.getLeaveMessageDate());
            map.put("isRead", leaveMessageRecord.getIsRead());

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

    @Override
    public DataMap newLeaveWord(int rows, int pageNum) {
        PageHelper.startPage(pageNum, rows);

        List<LeaveMessageRecord> allLeaveMessageRecord = leaveMessageMapper.getUserLeaveWord();
        PageInfo<LeaveMessageRecord> pageInfo = new PageInfo<>(allLeaveMessageRecord);

        JSONObject returnObject = new JSONObject();
        JSONArray arr = new JSONArray();

        JSONObject map;
        for (LeaveMessageRecord leaveMessageRecord : allLeaveMessageRecord) {
            map = new JSONObject();
//            String pagePath= "localhost:9001"+leaveMessageRecord.getPageName();

            map.put("id", leaveMessageRecord.getId());
            map.put("pagePath", leaveMessageRecord.getPageName());
            map.put("answerer", articleMapper.getUsernameByAnwserId(leaveMessageRecord.getAnswererId()));
            map.put("leaveWordDate", leaveMessageRecord.getLeaveMessageDate());
            map.put("leaveWordContent", leaveMessageRecord.getLeaveMessageContent());

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
