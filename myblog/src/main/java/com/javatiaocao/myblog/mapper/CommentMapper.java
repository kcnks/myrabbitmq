package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.CommentRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CommentMapper {
    List<CommentRecord> getAllComment();
}
