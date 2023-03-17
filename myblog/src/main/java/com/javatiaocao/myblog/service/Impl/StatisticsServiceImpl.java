package com.javatiaocao.myblog.service.Impl;

import com.javatiaocao.myblog.mapper.ArticleMapper;
import com.javatiaocao.myblog.mapper.VisitorMapper;
import com.javatiaocao.myblog.model.Article;
import com.javatiaocao.myblog.model.Visitor;
import com.javatiaocao.myblog.service.RedisService;
import com.javatiaocao.myblog.service.StatisticsService;
import com.javatiaocao.myblog.utils.DataMap;
import com.tiaozaowang.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    //总访问量
    private static final String TOTAL_VISITOR = "total_visitor";
    //当前页访问量
    private static final String PAGE_VISITOR = "page_visitor";

    @Autowired
    RedisServiceImpl redisServiceImpl;

    @Autowired
    VisitorMapper visitorMapper;

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    StringRedisServiceImpl stringRedisService;

    @Override
    public DataMap getVisitorNumByPageName(String pageName, HttpServletRequest request) {

        HashMap<String, Object> dataMap = new HashMap<>();


        String visitor = (String) request.getSession().getAttribute(pageName);
        if (visitor == null) {

            request.getSession().setAttribute(pageName, "yes");
        }

        //增加当前页面的访问人数
        long pageVisitor = updatePageVisitor(visitor, pageName);   //这个pageVisitor返回值没有意义

        //增加总访问人数
        long totalVisitor = redisServiceImpl.addVisitorTotalNumInRedis(StringUtil.VISITOR, TOTAL_VISITOR, 1);
        if (totalVisitor == 0) {
            totalVisitor = visitorMapper.getTotalVisitor();   //这里为什么能够获取到？？？？

            totalVisitor = redisServiceImpl.putVisitorNumInRedis(StringUtil.VISITOR, TOTAL_VISITOR, totalVisitor + 1);

        }

        dataMap.put("TOTAL_VISITOR", totalVisitor);
        dataMap.put("PAGE_VISITOR", pageVisitor);


        return DataMap.success().setData(dataMap);
    }


    //
    @Override
    public DataMap getStatisticsInfo(HttpServletRequest request) {

        HashMap<String, Object> dataMap = new HashMap<>();
        long allVisitor = redisServiceImpl.getVisitorInRedis(StringUtil.VISITOR, TOTAL_VISITOR);
        long yesterdayVisitor = redisServiceImpl.getVisitorInRedis(StringUtil.VISITOR, "yesterdayVisitor");
        long allUser = articleMapper.getAllUser();
        long articleNum = articleMapper.getArticleNum();

        dataMap.put("allVisitor", allVisitor);
        dataMap.put("yesterdayVisitor", yesterdayVisitor);
        dataMap.put("allUser", allUser);
        dataMap.put("articleNum", articleNum);

        //获取点赞量
        if (stringRedisService.hasKey(StringUtil.ARTICLE_THUMBS_UP)) {
            int thumbsUpNum = (int) stringRedisService.get(StringUtil.ARTICLE_THUMBS_UP);
            dataMap.put("articleThumbsUpNum", thumbsUpNum);
        } else {
            dataMap.put("articleThumbsUpNum", 0);
        }

        return DataMap.success().setData(dataMap);
    }


    //更新当前页面访问量
    private long updatePageVisitor(String visitor, String pageName) {

        Visitor thisVisitor;
        Long pageVisitor;

        //如果没有浏览器访问该页面，则访问量+1
        if (visitor == null) {
            pageVisitor = redisServiceImpl.addVisitorTotalNumInRedis(StringUtil.VISITOR, pageName, 1);
            //这里上面的pageVisitor是有用的
            if (pageVisitor == 0) {
                thisVisitor = visitorMapper.getVisitorNumBypageName(pageName);
                //如果redis没有命中，则去数据库中取
                if (thisVisitor != null) {
                    redisServiceImpl.putVisitorNumInRedis(StringUtil.VISITOR, pageName, pageVisitor + 1);
                } else {
                    return 0l;
                }
            }
        }

        return 0l;

    }


}
