package com.javatiaocao.myblog.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class User {

    private Integer id;
    private String phone;
    private String username;
    private String password;
    private String gender;
    private String trueName;
    private String birthday;
    private String email;
    private String personalBrief;
    private String avatarImgUrl;
    private String recentlyLanded;

    private List<Role> roles;

    public User(String phone, String trueName, String birthday, String email, String personalBrief) {
        this.phone = phone;
        this.trueName = trueName;
        this.birthday = birthday;
        this.email = email;
        this.personalBrief = personalBrief;
    }


}
