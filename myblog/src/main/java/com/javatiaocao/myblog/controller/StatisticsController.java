package com.javatiaocao.myblog.controller;


import com.javatiaocao.myblog.service.StatisticsService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import com.javatiaocao.myblog.utils.MD5Util;
import com.tiaozaowang.constant.CodeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class StatisticsController {


    @Autowired
    StatisticsService statisticsService;

    //记录网站的访客量以及访问量
    @GetMapping("/getVisitorNumByPageName")
    public String getVisitorNumByPageName(HttpServletRequest request, @RequestParam("pageName") String pageName) {

        try {

            int index = pageName.indexOf("/");
            if (index == -1) {
                pageName = "visitorNum";
            }

            DataMap data = statisticsService.getVisitorNumByPageName(pageName, request);
            return JsonResult.build(data).toJSON();

        } catch (Exception e) {
            log.error("StatisticsController_getVisitorNumByPageName Exception", e);

        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();

    }

    @GetMapping("/getStatisticsInfo")
    public String getStatisticsInfo(HttpServletRequest request) {

        try {

            DataMap data = statisticsService.getStatisticsInfo(request);
            return JsonResult.build(data).toJSON();

        } catch (Exception e) {
            log.error("StatisticsController_getVisitorNumByPageName Exception", e);

        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();

    }

}
