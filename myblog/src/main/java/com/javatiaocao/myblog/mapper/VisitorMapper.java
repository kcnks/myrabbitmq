package com.javatiaocao.myblog.mapper;


import com.javatiaocao.myblog.model.Visitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface VisitorMapper {


    long getTotalVisitor();

    Visitor getVisitorNumBypageName(String pageName);

    void insertVisitor(String pageName);

    void updateVisitor(String visitorNum, String pageName);
}
