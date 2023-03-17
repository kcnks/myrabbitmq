package com.javatiaocao.myblog.controller;


import com.javatiaocao.myblog.service.LeaveMessageService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LeaveMessageController {

    @Autowired
    LeaveMessageService leaveMessageService;

    @PostMapping("/getUserLeaveWord")
    public String getUserLeaveWord(@RequestParam(value = "rows") int rows, @RequestParam(value = "pageNum") int pageNum) {

        try {

            DataMap data = leaveMessageService.getUserLeaveWord(rows, pageNum);
            return JsonResult.build(data).toJSON();

        } catch (Exception e) {
            log.error("FeedbackController_getArticleManagement Exception", e);

        }

        return JsonResult.fail(com.tiaozaowang.constant.CodeType.SERVER_EXCEPTION).toJSON();

    }


    @GetMapping("/newLeaveWord")
    public String newLeaveWord(@RequestParam(value = "rows") int rows, @RequestParam(value = "pageNum") int pageNum) {

        try {

            DataMap data = leaveMessageService.newLeaveWord(rows, pageNum);
            return JsonResult.build(data).toJSON();

        } catch (Exception e) {
            log.error("FeedbackController_getArticleManagement Exception", e);

        }

        return JsonResult.fail(com.tiaozaowang.constant.CodeType.SERVER_EXCEPTION).toJSON();

    }

}
