package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.PrivateWord;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface PrivateWordMapper {
    List<PrivateWord> getAllPrivateWord();
}
