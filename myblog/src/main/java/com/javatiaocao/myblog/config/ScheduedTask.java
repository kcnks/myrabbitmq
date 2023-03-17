package com.javatiaocao.myblog.config;


import com.javatiaocao.myblog.mapper.VisitorMapper;
import com.javatiaocao.myblog.service.Impl.HashRedisServiceImpl;
import com.tiaozaowang.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class ScheduedTask {

    @Autowired
    VisitorMapper visitorMapper;
    @Autowired
    HashRedisServiceImpl hashRedisService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void statisticVisitorNum() {
        long oldVisitorNum = visitorMapper.getTotalVisitor();
        long newVisitorNum = Long.valueOf(hashRedisService.get(StringUtil.VISITOR, "total_visitor").toString());

        long yesterdayVisitor = newVisitorNum - oldVisitorNum;

        if (hashRedisService.hasHashKey(StringUtil.VISITOR, "yesterdayVisitor")) {
            hashRedisService.put(StringUtil.VISITOR, "yesterdayVisitor", yesterdayVisitor);
        } else {
            hashRedisService.put(StringUtil.VISITOR, "yesterdayVisitor", oldVisitorNum);
        }


        //Redis数据写到sql
        LinkedHashMap map = (LinkedHashMap) hashRedisService.getAllFieldAndValue(StringUtil.VISITOR);
        for (Object o : map.keySet()) {
            String pageName = String.valueOf(o);
            String visitorNum = String.valueOf(map.get(pageName));
            visitorMapper.updateVisitor(visitorNum, pageName);
            if (!"total_visitor".equals(pageName) && !"yesterdayVisitor".equals(pageName)) {
                hashRedisService.hashDelete(StringUtil.VISITOR, pageName);
            }
        }


    }
}
