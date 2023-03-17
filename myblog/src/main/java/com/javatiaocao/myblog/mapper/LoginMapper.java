package com.javatiaocao.myblog.mapper;

import com.javatiaocao.myblog.model.Role;
import com.javatiaocao.myblog.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface LoginMapper {
    public User selectByUsername(String username);

    public User selectByPhone(String phone);

    void updaterecentlyLanded(@Param("phone") String phone, @Param("formatDateForSix") String formatDateForSix);

    List<Role> queryRolesByPhone(String phone);
}
