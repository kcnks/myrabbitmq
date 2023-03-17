package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Feedback;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FeedbackMapper {
    List<Feedback> getAllFeedback();

    void insertFeedback(Feedback feedback);
}
