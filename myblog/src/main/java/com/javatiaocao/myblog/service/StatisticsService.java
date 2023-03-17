package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.utils.DataMap;

import javax.servlet.http.HttpServletRequest;

public interface StatisticsService {
    DataMap getVisitorNumByPageName(String pageName, HttpServletRequest request);

    DataMap getStatisticsInfo(HttpServletRequest request);
}
