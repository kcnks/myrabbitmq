package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.model.Friendlink;
import com.javatiaocao.myblog.utils.DataMap;

public interface FriendlinkService {
    DataMap getFriendLink();

    DataMap updateFriendLink(Friendlink friendlink);

    DataMap addFriendLink(Friendlink friendlink);

    DataMap deleteFriendLink(String id);
}
