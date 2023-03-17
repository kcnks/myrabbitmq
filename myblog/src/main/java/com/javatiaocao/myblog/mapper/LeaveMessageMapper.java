package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.LeaveMessageRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LeaveMessageMapper {
    List<LeaveMessageRecord> getUserLeaveWord();
}
