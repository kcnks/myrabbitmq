//package com.javatiaocao.myblog.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//
//@Controller
//public class LoginController {
//
//     @Autowired
//     private LoginService loginService;
//
//     @GetMapping("/index")
//     public String goIndex(){
//          return "index";
//     }
//
//     @PostMapping ("/login")
//     public String userLogin(String phone,String password, Model model) {
//
//          System.out.println(phone);
//          System.out.println(password);
//          System.out.println(loginService.checkin(phone,password));
//
//          if (phone != null) {
//               if(loginService.checkin(phone,password)){
//                    return "redirect:/index";
//               }else {
//                    model.addAttribute("msg","密码错误！！");
//                    //回到登录页面
//                    return "login";
//               }
//          }else {
//               model.addAttribute("msg","请输入手机号！！");
//
//               System.out.println("=====================");
//               //回到登录页面
//               return "login";
//          }
//     }
//}
