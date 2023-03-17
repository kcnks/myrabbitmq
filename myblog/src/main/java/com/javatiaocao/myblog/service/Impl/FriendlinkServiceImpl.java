package com.javatiaocao.myblog.service.Impl;

import com.javatiaocao.myblog.mapper.FriendlinkMapper;
import com.javatiaocao.myblog.model.Friendlink;
import com.javatiaocao.myblog.service.FriendlinkService;
import com.javatiaocao.myblog.utils.DataMap;
import com.tiaozaowang.constant.CodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FriendlinkServiceImpl implements FriendlinkService {

    @Autowired
    FriendlinkMapper friendlinkMapper;

    @Override
    public DataMap getFriendLink() {

        List<Friendlink> friendLink = friendlinkMapper.getFriendLink();
        //没有分页，直接返回list
        return DataMap.success().setData(friendLink);
    }

    //修改友链
    @Transactional
    @Override
    public DataMap updateFriendLink(Friendlink friendlink) {
        friendlinkMapper.updateFriendLink(friendlink);
        return DataMap.success(CodeType.UPDATE_FRIEND_LINK_SUCCESS).setData(friendlink.getId());   //为什么返回Id
    }

    //新增友链
    @Transactional     //再测试一下去除@Transactional和不返回Id会怎样
    @Override
    public DataMap addFriendLink(Friendlink friendlink) {
        friendlinkMapper.addFriendLink(friendlink);
        return DataMap.success(CodeType.ADD_FRIEND_LINK_SUCCESS).setData(friendlink.getId());   //为什么返回Id
    }

    @Override
    public DataMap deleteFriendLink(String id) {

        friendlinkMapper.deleteFriendLink(id);
        return DataMap.success(CodeType.DELETE_FRIEND_LINK_SUCCESS);
    }


}
