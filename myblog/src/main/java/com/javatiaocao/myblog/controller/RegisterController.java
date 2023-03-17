package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.service.RegisterService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import com.javatiaocao.myblog.utils.MD5Util;
import com.tiaozaowang.constant.CodeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/register")
    public String userRegister(User user, HttpServletRequest request) {

//        String phone = request.getParameter("phone");
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//        String gender = request.getParameter("gender");
//
//
////      System.out.println(gender);
//
//        User user = new User();
//        user.setPhone(phone);
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setGender(gender);
//
//
//
//        //System.out.println(user.toString());
//
//       registerService.saveUser(user);


        try {

            //判断用户名是否已注册
            if (registerService.userNameIsExist(user.getUsername())) {
                return JsonResult.fail(CodeType.USERNAME_EXIST).toJSON();
            }
            //判断手机号是否已注册
            if (registerService.userPhoneIsExist(user.getPhone())) {
                return JsonResult.fail(CodeType.PHONE_EXIST).toJSON();
            }
            //手机号未被注册，然后对密码进行加密
            MD5Util md5Util = new MD5Util();
            user.setPassword(md5Util.encode(user.getPassword()));

            //开始注册
            DataMap data = registerService.insertUser(user);
            return JsonResult.build(data).toJSON();

        } catch (Exception e) {
            log.error("RegisterController_userRegister Exception", user, e);

        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();

    }
}
