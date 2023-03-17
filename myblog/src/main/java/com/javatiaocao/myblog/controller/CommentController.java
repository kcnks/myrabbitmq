package com.javatiaocao.myblog.controller;

import com.javatiaocao.myblog.service.CommentService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CommentController {

    @Autowired
    CommentService commentService;

    @GetMapping("/newComment")
    public String newComment(@RequestParam(value = "rows") int rows, @RequestParam(value = "pageNum") int pageNum) {

        try {

            DataMap data = commentService.newComment(rows, pageNum);
            return JsonResult.build(data).toJSON();

        } catch (Exception e) {
            log.error("FeedbackController_getArticleManagement Exception", e);

        }

        return JsonResult.fail(com.tiaozaowang.constant.CodeType.SERVER_EXCEPTION).toJSON();

    }
}
