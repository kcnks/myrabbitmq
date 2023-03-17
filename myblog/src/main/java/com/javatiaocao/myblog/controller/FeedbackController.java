package com.javatiaocao.myblog.controller;


import com.javatiaocao.myblog.model.Feedback;
import com.javatiaocao.myblog.service.ArticleService;
import com.javatiaocao.myblog.service.FeedbackService;
import com.javatiaocao.myblog.utils.DataMap;
import com.javatiaocao.myblog.utils.JsonResult;
import com.javatiaocao.myblog.utils.TimeUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.tiaozaowang.constant.CodeType;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@Slf4j
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    ArticleService articleService;

    @GetMapping("/getAllFeedback")
    public String getAllFeedback(@RequestParam(value = "rows") int rows, @RequestParam(value = "pageNum") int pageNum) {

        try {

            DataMap data = feedbackService.getAllFeedback(rows, pageNum);
            return JsonResult.build(data).toJSON();

        } catch (Exception e) {
            log.error("FeedbackController_getArticleManagement Exception", e);

        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();

    }


    @PostMapping("/submitFeedback")
    public String submitFeedback(@RequestParam(value = "feedbackContent") String feedbackContent, @RequestParam(value = "contactInfo") String contactInfo,
                                 @AuthenticationPrincipal Principal principal, HttpServletRequest request) {

        try {
            Principal userPrincipal = request.getUserPrincipal();
            String phone = userPrincipal.getName();
            int userId = articleService.getUseridByPhone(phone);

            Feedback feedback = new Feedback(feedbackContent, contactInfo, userId, new TimeUtil().getFormatDateForSix());
            DataMap data = feedbackService.submitFeedback(feedback);
            return JsonResult.build(data).toJSON();

        } catch (Exception e) {
            log.error("FeedbackController_getArticleManagement Exception", e);

        }

        return JsonResult.fail(CodeType.SERVER_EXCEPTION).toJSON();

    }
}
