package com.javatiaocao.myblog.service.Impl;

import com.javatiaocao.myblog.mapper.RegisterMapper;
import com.javatiaocao.myblog.model.User;
import com.javatiaocao.myblog.service.RegisterService;
import com.javatiaocao.myblog.utils.DataMap;
import com.tiaozaowang.constant.CodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private RegisterMapper registerMapper;


    @Override
    public boolean userNameIsExist(String username) {
        User user = registerMapper.userNameIsExist(username);
        return user != null;
    }

    @Override
    public boolean userPhoneIsExist(String phone) {
        User user = registerMapper.userPhoneIsExist(phone);
        return user != null;
    }

    @Override
    public DataMap insertUser(User user) {
        //判断用户名中有无空格，有则输出格式异常
        String str = " ";
        Pattern pattern = Pattern.compile(user.getUsername());
        Matcher matcher = pattern.matcher(str);
        boolean result = matcher.find();


        //判断用户名是否异常：1用户名长度(数据库username  255长度)      2特殊字符：如 空格
        if (user.getUsername().length() > 35 || result) {
            return DataMap.fail(CodeType.USERNAME_FORMAT_ERROR);
        }

        //设置默认头像----因为avatarImgUrl非空
        if ("male".equals(user.getGender())) {
            user.setAvatarImgUrl("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=false&word=%E5%8D%A1%E9%80%9A%E5%9B%BE%E7%89%87%E5%A4%B4%E5%83%8F&hs=0&pn=0&spn=0&di=7169026086108397569&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&ie=utf-8&oe=utf-8&cl=2&lm=-1&cs=334636694%2C646858402&os=2021235227%2C217221555&simid=334636694%2C646858402&adpicid=0&lpn=0&ln=30&fr=ala&fm=&sme=&cg=head&bdtype=0&oriquery=%E5%8D%A1%E9%80%9A%E5%9B%BE%E7%89%87%E5%A4%B4%E5%83%8F&objurl=https%3A%2F%2Fc-ssl.dtstatic.com%2Fuploads%2Fblog%2F202106%2F07%2F20210607212402_82a8d.thumb.1000_0.jpeg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3B17tpwg2_z%26e3Bv54AzdH3Fks52AzdH3F%3Ft1%3D8n9c8988ab&gsm=&islist=&querylist=&dyTabStr=MCwzLDIsMSw2LDQsNSw3LDgsOQ%3D%3D");
        } else {
            user.setAvatarImgUrl("https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=false&word=%E5%8D%A1%E9%80%9A%E5%9B%BE%E7%89%87%E5%A4%B4%E5%83%8F&hs=0&pn=56&spn=0&di=7169026086108397569&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&ie=utf-8&oe=utf-8&cl=2&lm=-1&cs=3680732328%2C2310478831&os=1008620401%2C2763610359&simid=3680732328%2C2310478831&adpicid=0&lpn=0&ln=30&fr=ala&fm=&sme=&cg=head&bdtype=0&oriquery=%E5%8D%A1%E9%80%9A%E5%9B%BE%E7%89%87%E5%A4%B4%E5%83%8F&objurl=https%3A%2F%2Flmg.jj20.com%2Fup%2Fallimg%2Ftx29%2F081511174355210838.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3B33da_z%26e3Bv54AzdH3FpxAzdH3Fhwp5g2AzdH3Fn98nla_z%26e3Bip4s&gsm=1e&islist=&querylist=&dyTabStr=MCwzLDIsMSw2LDQsNSw3LDgsOQ%3D%3D");
        }


        //插入用户
        registerMapper.insertUser(user);


        User user1 = registerMapper.selectByPhone(user.getPhone());
        updateRoleByUserId(user1.getId(), 1);


        return DataMap.success();
    }

    private void updateRoleByUserId(Integer userId, int roleId) {
        registerMapper.updateRoleByUserId(userId, roleId);
    }


}
