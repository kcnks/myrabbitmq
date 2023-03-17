package com.javatiaocao.myblog.service.Impl;

import com.javatiaocao.myblog.mapper.ArticleMapper;
import com.javatiaocao.myblog.service.RedisService;
import com.javatiaocao.myblog.utils.DataMap;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;

@Service
public class RedisServiceImpl {

    @Autowired
    HashRedisServiceImpl hashRedisServiceImpl;
    @Autowired
    StringRedisServiceImpl stringRedisService;

    @Autowired
    ArticleMapper articleMapper;


    //增加redis中的访问量
    public long addVisitorTotalNumInRedis(String visitor, Object field, long i) {

        boolean fieldIsExist = hashRedisServiceImpl.hasHashKey(visitor, field);

        if (fieldIsExist) {
            Long a = hashRedisServiceImpl.hashIncrement(visitor, field, i);
            return Long.valueOf(hashRedisServiceImpl.get(visitor, field).toString());
        }
        //
        return 0l;


    }

    public long putVisitorNumInRedis(String visitor, Object field, Object value) {

        hashRedisServiceImpl.put(visitor, field, value);

        return Long.valueOf(hashRedisServiceImpl.get(visitor, field).toString());

    }

    public long getVisitorInRedis(String visitor, String totalVisitor) {

        boolean b = hashRedisServiceImpl.hasHashKey(visitor, totalVisitor);
        if (b) {
            return Long.valueOf(hashRedisServiceImpl.get(visitor, totalVisitor).toString());
        }
        return 0l;
    }

    public void readThumbsUpInRedis(String articleThumbsUp, int i) {
        Boolean key = stringRedisService.hasKey(articleThumbsUp);
        if (!key) {
            stringRedisService.set(articleThumbsUp, 1);

        } else {
            stringRedisService.stringIncrement(articleThumbsUp, i);
        }

    }


    public DataMap getUserNews(String phone) {
        int userId = articleMapper.getUseridByPhone(phone);
        HashMap<String, Object> dataMap = new HashMap<>();
        JSONObject returnData = new JSONObject();
        LinkedHashMap d = (LinkedHashMap) hashRedisServiceImpl.getAllFieldAndValue(String.valueOf(userId));

        if (d.size() == 0) {
            returnData.put("result", 0);
        } else {

            int allNewsNum = (int) d.get("allNewsNum");
            int commentNum = (int) d.get("commentNum");
            int leaveMessageNum = (int) d.get("leaveMessageNum");
            dataMap.put("allNewsNum", allNewsNum);
            dataMap.put("commentNum", commentNum);
            dataMap.put("leaveMessageNum", leaveMessageNum);

            returnData.put("result", dataMap);
        }
        return DataMap.success().setData(returnData);
    }
}
