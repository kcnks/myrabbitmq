package com.javatiaocao.myblog.service.Impl;

import com.javatiaocao.myblog.mapper.LoginMapper;
import com.javatiaocao.myblog.model.Role;
import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.utils.TimeUtil;
import com.tiaozaowang.constant.CodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class CustomUserServiceImpl implements UserDetailsService {

    @Autowired
    private LoginMapper loginMapper;


    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {


        User user = loginMapper.selectByPhone(phone);
        //判断
        if (user == null) {
            //数据库没有用户名，认证失败
            throw new UsernameNotFoundException(CodeType.USERNAME_NOT_EXIST.name());
        }

        List<Role> roles = loginMapper.queryRolesByPhone(phone);
        user.setRoles(roles);

        //更新登录时间
        TimeUtil timeUtil = new TimeUtil();
        String formatDateForSix = timeUtil.getFormatDateForSix();
        loginMapper.updaterecentlyLanded(user.getPhone(), formatDateForSix);

        //获取查询出的用户的所有权限
        ArrayList<SimpleGrantedAuthority> auths = new ArrayList<>();
        for (Role role : user.getRoles()) {
            auths.add(new SimpleGrantedAuthority(role.getName()));
        }

        //从查询数据库返回user对象，得到用户名和密码，返回
        return new org.springframework.security.core.userdetails.User(user.getPhone(), user.getPassword(), auths);


    }
}
