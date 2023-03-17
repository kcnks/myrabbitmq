package com.javatiaocao.myblog.service;

import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.utils.DataMap;

public interface RegisterService {

    //判断用户名是否存在
    boolean userNameIsExist(String username);

    //注册用户
    DataMap insertUser(User user);

    boolean userPhoneIsExist(String phone);

//    public void saveUser(User user);

}
