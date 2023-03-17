package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RegisterMapper {

    public void insertUser(User user);

    User userNameIsExist(String username);

    User userPhoneIsExist(String phone);

    public User selectByPhone(String phone);

    void updateRoleByUserId(@Param("userId") Integer userId, @Param("roleId") int roleId);
}
