package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.model.Friendlink;
import com.javatiaocao.myblog.service.FriendlinkService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import com.tiaozaowang.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.tiaozaowang.constant.CodeType;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


@RestController
@Slf4j
public class FriendlinkController {

    @Autowired
    FriendlinkService friendlinkService;

    //查询友链列表接口
    @PostMapping("/getFriendLink")
    public String getFriendLink() {

        try {

            DataMap data = friendlinkService.getFriendLink();
            return JsonResult.build(data).toJSON();
        } catch (Exception e) {
            log.error("ArticleController_getArticleManagement Exception", e);

        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();

    }


    //更新或者新建友链链接
    @PostMapping("/updateFriendLink")
    public String addOrUpdateFriendLink(@RequestParam("id") String id, @RequestParam("blogger") String blogger, @RequestParam("url") String url) {

        try {

            DataMap data;

            if (!StringUtil.BLANK.equals(id)) {   //判断出了问题
                //修改友链链接
                Friendlink friendlink = new Friendlink(Integer.parseInt(id), blogger, url);
                data = friendlinkService.updateFriendLink(friendlink);

            } else {

                //新增友链
                Friendlink friendlink = new Friendlink(blogger, url);
                data = friendlinkService.addFriendLink(friendlink);
            }
            return JsonResult.build(data).toJSON();

        } catch (Exception e) {
            log.error("ArticleController_getArticleManagement Exception", e);

        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();

    }


    //删除友链链接
    @PostMapping("/deleteFriendLink")
    public String deleteFriendLink(@RequestParam("id") String id) {

        try {


            DataMap data = friendlinkService.deleteFriendLink(id);
            return JsonResult.build(data).toJSON();

        } catch (Exception e) {
            log.error("ArticleController_getArticleManagement Exception", e);

        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();

    }


}





