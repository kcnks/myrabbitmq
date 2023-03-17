package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Friendlink;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface FriendlinkMapper {

    List<Friendlink> getFriendLink();

    void updateFriendLink(Friendlink friendlink);

    void addFriendLink(Friendlink friendlink);

    void deleteFriendLink(String id);
}
